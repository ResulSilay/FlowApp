package app.flow.ui.model

import android.graphics.Bitmap
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import app.flow.BR

class PostModel : BaseObservable() {

    var img: String? = null
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.img)
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

    var picture: String = ""
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.picture)
        }

    var images: List<String> = arrayListOf()
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.images)
        }

    var rate: Float = 0.0f
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.rate)
        }

    var createdDateTime: String = ""
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.createdDateTime)
        }
}