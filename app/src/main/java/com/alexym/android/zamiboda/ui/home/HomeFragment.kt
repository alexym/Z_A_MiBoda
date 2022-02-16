package com.alexym.android.zamiboda.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexym.android.zamiboda.R
import com.alexym.android.zamiboda.databinding.FragmentHomeBBinding
import com.alexym.android.zamiboda.utils.konfetti.Presets.Companion.explode
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.xml.KonfettiView
import nl.dionsegijn.konfetti.xml.listeners.OnParticleSystemUpdateListener


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBBinding? = null
    val TAG = "nested_sync"

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
        homeViewModel.isToday.observe(viewLifecycleOwner, Observer {
            if (it)
                initKonfetti()
        })

        val animation = AnimationUtils.loadAnimation(context, R.anim.arrow).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
        }
        binding.arrowDownIv.startAnimation(animation)
//        binding.scrollable.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            val TAG = "nested_sync"
//            Log.e(TAG, scrollX.toString())
//        })
//        binding.scrollable.setOnScrollChangeListener(ViewTreeObserver.OnScrollChangedListener {
//
//            val diff: Int =
//                binding.scrollable.getChildAt(binding.scrollable.getChildCount() - 1).bottom - (binding.scrollable.getHeight() + binding.scrollable
//                    .getScrollY())
//
//            if (diff == 0) {
//                Log.i(TAG, "Scroll DOWN")
//            }
//        })
        binding.scrollable.setScrollListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.i(TAG, "se escrolea")
            if (scrollY > oldScrollY) {
                Log.i(TAG, "Scroll DOWN")
            }
            if (scrollY < oldScrollY) {
                Log.i(TAG, "Scroll UP")
            }
            if (scrollY == 0) {
                Log.i(TAG, "TOP SCROLL")
            }
            if (scrollY == v.measuredHeight - v.getChildAt(0).measuredHeight) {
                Log.i(TAG, "BOTTOM SCROLL")
            }
        })
//        binding.scrollable.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            run {
////                if (scrollY < 0) {
////                    binding.arrowDownIv.visibility = GONE
////                }
//                val TAG = "nested_sync"
//                Log.i(TAG, scrollX.toString())
//                if (scrollY > oldScrollY) {
//                    Log.i(TAG, "Scroll DOWN")
//                }
//                if (scrollY < oldScrollY) {
//                    Log.i(TAG, "Scroll UP")
//                }
//
//                if (scrollY == 0) {
//                    Log.i(TAG, "TOP SCROLL")
//                }
//
//                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
//                    Log.i(TAG, "BOTTOM SCROLL")
////                    if (!isRecyclerViewWaitingtoLaadData) //check for scroll down
////                    {
////                        if (!loadedAllItems) {
////                            showUnSentData()
////                        }
////                    }
//                }
//            }
//        })
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