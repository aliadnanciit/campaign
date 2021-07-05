package de.westwing.campaignbrowser.model

import android.os.Parcel
import android.os.Parcelable

data class Campaign(
    val id: Long = 0,
    val name: String,
    val description: String,
    val imageUrl: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Campaign> {
        override fun createFromParcel(parcel: Parcel): Campaign {
            return Campaign(parcel)
        }

        override fun newArray(size: Int): Array<Campaign?> {
            return arrayOfNulls(size)
        }
    }
}