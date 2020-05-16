package app.flow.ui.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import app.flow.BR

class UserModel : BaseObservable() {
    var img: String? = ""
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

    var email: String = ""
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    var password: String = ""
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    var phone: String = ""
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }

    var address: String = ""
        @Bindable get
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.address)
        }
}