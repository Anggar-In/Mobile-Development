package com.example.anggarin.ui.tabungan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.databinding.ActivityDetailTabunganBinding
import com.example.anggarin.databinding.BottomsheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailTabunganActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTabunganBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailTabunganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambahTabungan.setOnClickListener {
          showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheetTabungan = BottomSheetDialog(this)
        val bottomSheetBinding = BottomsheetDialogBinding.inflate(layoutInflater)

        bottomSheetTabungan.apply {
            setContentView(bottomSheetBinding.root)
            show()
        }

        bottomSheetBinding.btnBatal.setOnClickListener{
            bottomSheetTabungan.dismiss()
        }
    }
}