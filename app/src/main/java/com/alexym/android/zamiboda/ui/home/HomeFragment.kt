package com.alexym.android.zamiboda.ui.home

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexym.android.zamiboda.R
import com.alexym.android.zamiboda.databinding.FragmentHomeBBinding
import com.alexym.android.zamiboda.databinding.FragmentHomeBinding
import com.alexym.android.zamiboda.utils.konfetti.Presets.Companion.explode
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.blurry.Blurry
import jp.wasabeef.glide.transformations.BlurTransformation
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.xml.KonfettiView
import nl.dionsegijn.konfetti.xml.listeners.OnParticleSystemUpdateListener


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
//        initKonfetti()
        homeViewModel.days.observe(viewLifecycleOwner, Observer {
            binding.dayCountTv.text = it
        })
        homeViewModel.hours.observe(viewLifecycleOwner, Observer {
            binding.hourCountTv.text = it
        })
        homeViewModel.minutes.observe(viewLifecycleOwner, Observer {
            binding.minutesCountTv.text = it
        })
        homeViewModel.seconds.observe(viewLifecycleOwner, Observer {
            binding.secondsCountTv.text = it
        })
        // Sync
//        val bitmap = Blurry.with(activity)
//            .radius(10)
//            .sampling(8)
//            .capture(findViewById(R.id.right_bottom)).get()
//        imageView.setImageDrawable(BitmapDrawable(resources, bitmap))

// Async
//        Blurry.with(activity)
//            .radius(25)
//            .sampling(4)
//            .color(Color.argb(66, 255, 255, 0))
//            .capture(binding.imageView2)
//            .getAsync {
//                binding.imageView2.setImageDrawable(BitmapDrawable(resources, it))
//            }
//        context?.let {
//            Glide.with(it).load(resources.getDrawable(R.drawable.wedding))
//                .apply(bitmapTransform( BlurTransformation(12)))
//                .into(binding.imageView2)
//        };
    }

    private fun initKonfetti() {
        binding.konfettiView.start(explode())
        binding.konfettiView.onParticleSystemUpdateListener = object :
            OnParticleSystemUpdateListener {
            override fun onParticleSystemEnded(
                view: KonfettiView,
                party: Party,
                activeSystems: Int
            ) {
                binding.konfettiView.reset()
                binding.konfettiView.start(explode())
            }

            override fun onParticleSystemStarted(
                view: KonfettiView,
                party: Party,
                activeSystems: Int
            ) {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}