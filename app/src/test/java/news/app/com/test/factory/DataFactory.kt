package news.app.com.test.factory

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    fun randomString():String{
        return UUID.randomUUID().toString()
    }

    fun randomDate(): String{
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(Date())
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
}