package site.soymegh.controlrifas.data.config

import android.app.Application
import android.util.Log
import androidx.room.Room

class ControlApplication:Application() {
    companion object{
        lateinit var db:DataBaseControl
    }

    override fun onCreate() {

        super.onCreate()

        db = Room.databaseBuilder(
            this,
            DataBaseControl::class.java,
            "controldb"
        ).build()

    }
}