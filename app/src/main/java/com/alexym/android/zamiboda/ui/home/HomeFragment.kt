package com.alexym.android.zamiboda.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexym.android.zamiboda.databinding.FragmentHomeBinding
import com.alexym.android.zamiboda.utils.konfetti.Presets.Companion.explode
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.xml.KonfettiView
import nl.dionsegijn.konfetti.xml.listeners.OnParticleSystemUpdateListener


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        initKonfetti()
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