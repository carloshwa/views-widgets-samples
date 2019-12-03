package androidx.viewpager2.integration.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_viewpager2.view_pager

class PreviewPagesFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.activity_viewpager2, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view_pager.apply {
      // Set offscreen page limit to at least 1, so adjacent pages are always laid out
      offscreenPageLimit = 1
      adapter = Adapter()
    }
  }

  override fun onResume() {
    super.onResume()

    view_pager.postDelayed(1000) {
      val recyclerView = view_pager.getChildAt(0) as RecyclerView
      recyclerView.apply {
        val padding = resources.getDimensionPixelOffset(R.dimen.halfPageMargin) +
            resources.getDimensionPixelOffset(R.dimen.peekOffset)
        // setting padding on inner RecyclerView puts overscroll effect in the right place
        // TODO: expose in later versions not to rely on getChildAt(0) which might break
        setPadding(padding, 0, padding, 0)
        clipToPadding = false
      }
    }
  }

  class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_preview_pages, parent, false)
  )

  class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
      return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      holder.itemView.tag = position
    }
  }
}