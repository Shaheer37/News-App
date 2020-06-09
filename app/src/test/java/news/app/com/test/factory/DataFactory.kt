package news.app.com.test.factory

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    public const val dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun getLocale() = Locale.US;

    fun randomString():String{
        return UUID.randomUUID().toString()
    }

    fun randomDate(): String{
        return SimpleDateFormat(dateFormat).format(Date())
    }

    fun randomYear(): Int {
        return ThreadLocalRandom.current().nextInt(2000, 2018 + 1)
    }

    fun randomMonth(): Int {
        return ThreadLocalRandom.current().nextInt(0, 12 + 1)
    }

    fun randomDay(): Int {
        return ThreadLocalRandom.current().nextInt(0, 30 + 1)
    }

    fun randomLink(): String{
        return "https://cdn.cnn.com/cnnnext/dam/assets/200523112239-02-nick-cordero-super-tease.jpg";
    }
}