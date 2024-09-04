package com.prac.sampleappxml.activity

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prac.sampleappxml.R
import com.prac.sampleappxml.activity.adapters.CarouselAdapter
import com.prac.sampleappxml.activity.adapters.MemeAdapter
import com.prac.sampleappxml.activity.adapters.OnItemClickListener
import com.prac.sampleappxml.activity.models.Meme
import com.prac.sampleappxml.databinding.ActivityMainBinding
import com.prac.sampleappxml.databinding.DialogStatisticsBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private var dotsLayout: LinearLayout? = null
    private lateinit var dots: Array<TextView?>
    private var listOfImages: List<String?>? = null
    private lateinit var viewModel: MemeViewModel
    private val memeList = ArrayList<Meme>()

    // listener for viewpager
    // handle scrolling behaviour
    private val viewListener = object: ViewPager.OnPageChangeListener{
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            setUpIndicator(position, listOfImages?.size?:0)
        }

        override fun onPageScrollStateChanged(state: Int) {
        }

    }

    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enable viewBinding to access views directly throughout the activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MemeViewModel::class.java]

        // sync meme list
        viewModel.syncMemeList(40)

        // get reference of dots layout
        dotsLayout = binding.indicatorDots

        // set up list
        setUpList()

        // create and setUp carousel view of images with indicator selection using viewpager
        setUpCarousel()

        // setUp search memes
        setUpSearch()

        // handle fab click
        binding.fab.setOnClickListener {
            val currentPageStatistics = computePageStatistics(memeList)
            showStatisticsBottomSheet(currentPageStatistics)
        }
    }

    /**
     * this method used to handle search text watcher
     */
    private fun setUpSearch() {
        try{
            binding.searchEdt.addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {
                    if(memeList.isNotEmpty() && !str.isNullOrEmpty()){
                        val list = memeList.filter {
                            (it.title?.contains(str, true) == true ||
                             it.subTitle?.contains(str, true) == true)
                        }
                        binding.recyclerView.adapter = MemeAdapter(this@MainActivity, list, this@MainActivity)
                    } else{
                        binding.recyclerView.adapter = MemeAdapter(this@MainActivity, memeList, this@MainActivity)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
        } catch(e: Exception){
            Log.e(TAG, "Error in setUpSearch", e)
        }
    }

    /**
     * this method setUp meme list
     */
    private fun setUpList() {
        try{
            // create adapter
            val adapter = MemeAdapter(
                context = this,
                memeList,
                this
            )
            // set recycler view
            binding.recyclerView.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(this.context)
                setHasFixedSize(true)
            }

            // observe meme list
            viewModel.memeList.observe(this) {
                binding.pbarRv.visibility = View.GONE
                memeList.clear()
                memeList.addAll(it)
                binding.recyclerView.adapter = MemeAdapter(this, memeList, this)
            }
        } catch(e: Exception){
            Log.e(TAG, "Error in setUpCarousel", e)
        }
    }

    /**
     * create and setUp carousel view of images with indicator selection using viewpager
     */
    private fun setUpCarousel() {
        try{
            // observe meme list from view model
            viewModel.memeList.observe(this) {

                binding.pbarCr.visibility = View.GONE

                if(listOfImages == null){
                    listOfImages = ArrayList()
                }

                // created image list from meme list data
                val urls = it.map {  item -> item.image }.shuffled().take(5)
                listOfImages = urls

                // update carousel when list of images is not empty
                binding.slidingViewPager.adapter = CarouselAdapter(this, listOfImages!!)
                setUpIndicator(0, listOfImages?.size?:0)
                binding.slidingViewPager.addOnPageChangeListener(viewListener)
            }

            // setUp carousel
            if(!listOfImages.isNullOrEmpty()){
                binding.slidingViewPager.adapter = CarouselAdapter(this, listOfImages!!)
                setUpIndicator(0, listOfImages?.size?:0)
                binding.slidingViewPager.addOnPageChangeListener(viewListener)
            }
        } catch(e: Exception){
            Log.e(TAG, "Error in setUpCarousel", e)
        }
    }


    /**
     * this method setUp carousel indicator
     */
    fun setUpIndicator(position: Int, size: Int) {
        dots = arrayOfNulls(size)
        dotsLayout?.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this).apply {
                text = Html.fromHtml("&#8226;")
                textSize = 35f
                setTextColor(resources.getColor(R.color.inactive, null))
            }
            dotsLayout?.addView(dots[i])
        }

        dots[position]?.setTextColor(resources.getColor(R.color.active, null))
    }

    /**
     * this method computes page statistics
     */
    private fun computePageStatistics(items: List<Meme>): Map<Char, Int> {
        val charFrequency = mutableMapOf<Char, Int>()

        items.forEach { meme ->
            meme.title?.forEach { char ->
                if (char.isLetter()) {
                    charFrequency[char] = charFrequency.getOrDefault(char, 0) + 1
                }
            }
        }

        return charFrequency
            .toList()
            .sortedByDescending { it.second }
            .take(3)
            .toMap()
    }

    /**
     * this method shows statistics top characters and their count in bottom sheet
     */
    private fun showStatisticsBottomSheet(statistics: Map<Char, Int>) {
        val dialog = BottomSheetDialog(this)
        val binding = DialogStatisticsBinding.inflate(layoutInflater)

        val builder = StringBuilder("")

        statistics.forEach { (key, value) ->
            builder.append("Character: ")
            builder.append(key)
            builder.append(" = ")
            builder.append("Count: ")
            builder.append(value)
            builder.append("\n")
        }

        // setUp top characters info in bottom sheet
        binding.topCharacters.text = builder.toString()

        dialog.setContentView(binding.root)
        dialog.show()
    }

    // handles item click
    override fun onItemClicked(item: Meme) {
        // no need to implement
    }
}