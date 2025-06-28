package com.kaimaki.usuario.usuario_fronted_movil.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaimaki.usuario.usuario_fronted_movil.databinding.ItemOnboardingBinding
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.OnboardingItem

class OnboardingAdapter(private val items: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            imageView.setImageResource(item.imageRes)
            textTitle.text = item.title
            textDescription.text = item.description
        }
    }

    override fun getItemCount() = items.size
}
