package com.example.keybladeviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class Keyblade implements Parcelable{
	String name;
	String strength;
	String magic;
	String ability;
	
	public Keyblade() {
		this.name = "";
		this.strength = "";
		this.magic = "";
		this.ability = "";
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(strength);
		dest.writeString(magic);
		dest.writeString(ability);
	}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Keyblade> CREATOR = new Parcelable.Creator<Keyblade>() {
        public Keyblade createFromParcel(Parcel in) {
            return new Keyblade(in);
        }

        public Keyblade[] newArray(int size) {
            return new Keyblade[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Keyblade(Parcel in) {
    	name = in.readString();
        strength = in.readString();
        magic = in.readString();
        ability = in.readString();
    }
}
