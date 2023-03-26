package uz.devapp.elonuz.screen.main.add_ad

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import uz.devapp.elonuz.data.model.CategoryModel
import uz.devapp.elonuz.data.model.DistrictModel
import uz.devapp.elonuz.data.model.EventModel
import uz.devapp.elonuz.databinding.ActivityAddAdsBinding
import uz.devapp.elonuz.screen.main.MainActivity
import uz.devapp.elonuz.screen.main.add_ad.category.SelectCategoryActivity
import uz.devapp.elonuz.screen.main.add_ad.region.SelectRegionActivity
import uz.devapp.elonuz.utils.Constants
import uz.devapp.elonuz.utils.showMessage


class AddAdsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddAdsBinding
    lateinit var viewModel: AddAdsViewModel

    var selectedImagePath = ""
    var selectedDistrict: DistrictModel? = null
    var selectedCategory: CategoryModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        viewModel = ViewModelProvider(this)[AddAdsViewModel::class.java]

        viewModel.errorLiveData.observe(this, Observer {
            showMessage(it)
        })

        viewModel.progressLiveData.observe(this) {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.addData.observe(this) {
            showMessage("E'lon qo'shildi!")
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        binding.imgBack.setOnClickListener { onBackPressed() }

        binding.cardViewSelectImage.setOnClickListener {
            ImagePicker.with(this)
                .provider(ImageProvider.BOTH)
                .crop()//Or bothCameraGallery()
                .createIntentFromDialog { launcher.launch(it) }
        }

        binding.edDistrict.setOnClickListener {
            startActivity(Intent(this, SelectRegionActivity::class.java))
        }

        binding.edCategory.setOnClickListener {
            startActivity(Intent(this, SelectCategoryActivity::class.java))
        }

        binding.btnSave.setOnClickListener {
            if (selectedImagePath.isNotEmpty() && selectedCategory != null && selectedDistrict != null && binding.edName.text!!.isNotEmpty()) {
                viewModel.addAds(
                    selectedImagePath,
                    binding.edName.text.toString(),
                    binding.edComment.text.toString(),
                    binding.edAddress.text.toString(),
                    binding.edPhone.text.toString(),
                    binding.edPrice.text.toString().toDouble(),
                    selectedCategory!!.id,
                    selectedDistrict!!.id,
                )
            } else {
                showMessage("Iltimos hamma maydonlarni to'ldiring!")
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                // Use the uri to load the image
                // Only if you are not using crop feature:
                uri.let { galleryUri ->
                    selectedImagePath = galleryUri.path ?: ""
                    Glide.with(this)
                        .load(galleryUri)
                        .into(binding.imgAds)
                }
                //////////////
            }
        }


    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: EventModel) {
        if (event.command == Constants.EVENT_SELECT_REGION) {
            selectedDistrict = event.data as DistrictModel
            binding.edDistrict.setText(selectedDistrict?.name_uz)
        } else if (event.command == Constants.EVENT_SELECT_CATEGORY) {
            selectedCategory = event.data as CategoryModel
            binding.edCategory.setText(selectedCategory?.title)
        }
    }
}