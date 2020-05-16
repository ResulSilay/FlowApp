package app.flow.ui.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import app.flow.BR

class RateModel : BaseObservable() {
    var rate: Float = 0f
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.rate)
        }
}