package com.example.anggarin.ui.investasi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anggarin.R
import com.example.anggarin.data.response.InvestmentRecommendation
import java.math.BigDecimal
import java.math.RoundingMode

class InvestasiAdapter(private var investmentList: List<InvestmentRecommendation>,
                       private var targetReturn: Double,
                       private val onDetailsClicked: (InvestmentRecommendation) -> Unit)
    : RecyclerView.Adapter<InvestasiAdapter.InvestmentViewHolder>() {

    // ViewHolder untuk menyimpan elemen dari layout item
    class InvestmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNamaSaham: TextView = itemView.findViewById(R.id.txt_nama_saham)
        val txtHarga: TextView = itemView.findViewById(R.id.txt_harga)
        val txtKolomROI: TextView = itemView.findViewById(R.id.txt_kolom_roi)
        val txtOneYearsReturn: TextView = itemView.findViewById(R.id.txt_oneyearsreturn)
        val btnSelengkapnya: LinearLayout = itemView.findViewById(R.id.btn_selengkapnya)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_investasi, parent, false)
        return InvestmentViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: InvestmentViewHolder, position: Int) {
        val investment = investmentList[position]

        // Pembulatan targetReturn ke dua desimal menggunakan BigDecimal
        val roundedTargetReturn = BigDecimal(targetReturn).setScale(2, RoundingMode.HALF_UP).toDouble()

        // Menampilkan data di layout item
        holder.txtNamaSaham.text = investment.stockCode
        holder.txtHarga.text = investment.marketCap
        holder.txtKolomROI.text = "$roundedTargetReturn"
        holder.txtOneYearsReturn.text = "one years return (${investment.oneYearPriceReturns ?: 0})"

        // Atur klik listener untuk tombol "Selengkapnya"
        holder.btnSelengkapnya.setOnClickListener {
            onDetailsClicked(investment) // Panggil callback dengan data yang sesuai
        }
    }

    override fun getItemCount(): Int = investmentList.size

    // Fungsi untuk memperbarui data
    fun updateData(newData: List<InvestmentRecommendation>, newTargetReturn: Double) {
        investmentList = newData
        targetReturn = newTargetReturn
        notifyDataSetChanged()
    }
}


