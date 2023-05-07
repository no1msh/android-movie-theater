package woowacourse.movie.fragment.bookhistory

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.BookHistories
import woowacourse.movie.BookHistoryRecyclerViewAdapter
import woowacourse.movie.BookingHistoryRepository
import woowacourse.movie.R
import woowacourse.movie.activity.bookcomplete.BookCompleteActivity

class BookHistoryFragment : Fragment(R.layout.fragment_book_history), BookHistoryContract.View {

    override lateinit var presenter: BookHistoryContract.Presenter
    private val bookHistory: BookingHistoryRepository by lazy {
        BookingHistoryRepository(BookHistories.getDBInstance(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = BookHistoryPresenter(this)
        super.onViewCreated(view, savedInstanceState)
        if (!bookHistory.isEmpty()) {
            reloadBookingData()
        }
        setMovieRecyclerView(view)
    }

    private fun reloadBookingData() {
        BookHistories.items.clear()
        BookHistories.items.addAll(bookHistory.getAll())
    }

    override fun showDetailPage(dataPosition: Int) {
        startActivity(
            BookCompleteActivity.intent(
                requireContext(),
                presenter.getData()[dataPosition]
            )
        )
    }

    private fun setMovieRecyclerView(view: View) {
        val movieRecyclerView: RecyclerView = requireView().findViewById(R.id.rv_book_history_list)
        movieRecyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayout.VERTICAL
            )
        )

        val bookHistoryRecyclerViewAdapter = BookHistoryRecyclerViewAdapter(
            presenter.getData(),
            getBookHistoryOnClickListener()
        )

        movieRecyclerView.adapter = bookHistoryRecyclerViewAdapter
        bookHistoryRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun getBookHistoryOnClickListener() = { position: Int ->
        showDetailPage(position)
    }
}