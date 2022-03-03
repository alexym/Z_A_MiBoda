package com.alexym.android.zamiboda.ui.tomanota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexym.android.zamiboda.R
import com.alexym.android.zamiboda.databinding.FragmentSlideshowBinding

class TomaNotaFragment : Fragment() {

    private lateinit var slideshowViewModel: TomaNotaViewModel
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this)[TomaNotaViewModel::class.java]

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val animation = AnimationUtils.loadAnimation(context, R.anim.flower_scale).apply {
            duration = 1500
            interpolator = AccelerateDecelerateInterpolator()
        }
        binding.topCorner.startAnimation(animation)
        binding.topCornerB.startAnimation(animation)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}