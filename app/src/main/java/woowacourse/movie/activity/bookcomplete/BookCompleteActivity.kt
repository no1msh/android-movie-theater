package woowacourse.movie.activity.bookcomplete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import woowacourse.movie.BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY
import woowacourse.movie.R
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.movie.MovieBookingSeatInfo

class BookCompleteActivity : BackButtonActivity(), BookCompleteContract.View {
    override lateinit var presenter: BookCompleteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_complete)

        val movieBookingData = getMovieBookingSeatInfo()
        presenter = BookCompletePresenter(this)
        progressIfDummyData(movieBookingData)
        presenter.initBookCompletePage(movieBookingData)
    }

    private fun getMovieBookingSeatInfo(): MovieBookingSeatInfo {
        return intent.getSerializableCompat(MOVIE_BOOKING_SEAT_INFO_KEY)
            ?: MovieBookingSeatInfo.dummyData
    }

    override fun progressIfDummyData(movieBookingSeatInfo: MovieBookingSeatInfo) {
        if (movieBookingSeatInfo == MovieBookingSeatInfo.dummyData) {
            Toast.makeText(
                this,
                getString(R.string.cant_get_movie_booking_data),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun initMovieTitle(movieTitle: String) {
        findViewById<TextView>(R.id.tv_book_movie_title).text = movieTitle
    }

    override fun initBookDate(bookingTime: String) {
        findViewById<TextView>(R.id.tv_book_date).text = bookingTime
    }

    override fun initBookPersonCount(ticketCount: Int, seats: List<String>) {
        findViewById<TextView>(R.id.tv_book_person_count).text =
            getString(
                R.string.book_person_count,
                ticketCount,
                seats.joinToString(", ")
            )
    }

    override fun initBookTotalPrice(totalPrice: String) {
        findViewById<TextView>(R.id.tv_book_total_pay).text =
            getString(R.string.book_total_pay, totalPrice)
    }

    companion object {
        fun intent(context: Context, data: MovieBookingSeatInfo) =
            Intent(context, BookCompleteActivity::class.java).putExtra(
                MOVIE_BOOKING_SEAT_INFO_KEY,
                data
            )
    }
}