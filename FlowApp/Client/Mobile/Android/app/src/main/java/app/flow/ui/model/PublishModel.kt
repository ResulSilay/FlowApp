package app.flow.ui.model

import android.graphics.Bitmap
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import app.flow.BR

class PublishModel : BaseObservable() {

    var picture: Bitmap? = null
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.picture)
        }

    var name: String = ""
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    var content: String = ""
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    var images: ArrayList<String> = ArrayList()
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.images)
        }
}