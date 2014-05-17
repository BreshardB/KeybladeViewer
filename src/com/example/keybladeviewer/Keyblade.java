package com.example.keybladeviewer;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

//Class to hold necessary information for each keyblade
public class Keyblade implements Parcelable{
	String name;
	String strength;
	String magic;
	String ability;
	String critRate;
	String critBonus;
	String recoil;
	String length;
	//Drawable image;
	
	public Keyblade() {
		this.name = "";
		this.strength = "";
		this.magic = "";
		this.ability = "";
		this.critRate = "";
		this.critBonus = "";
		this.recoil = "";
		this.length = "";
	}
	
	//Parcelable code gathered from here:
	//http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
	
	// example constructor that takes a Parcel and gives you an object populated with it's values
    public Keyblade(Parcel in) {
    	name = in.readString();
        strength = in.readString();
        magic = in.readString();
        ability = in.readString();
        critRate = in.readString();
        critBonus = in.readString();
        recoil = in.readString();
        length = in.readString();
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
		dest.writeString(critRate);
		dest.writeString(critBonus);
		dest.writeString(recoil);
		dest.writeString(length);
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
}
