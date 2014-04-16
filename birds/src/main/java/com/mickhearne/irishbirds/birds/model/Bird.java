package com.mickhearne.irishbirds.birds.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bird implements Parcelable {
	private long id;
	private String name;
	private String latin_name;
	private String status;
	private String identification;
	private String diet;
	private String breeding;
	private String wintering_habits;
	private String where_to_see;
	private String conservation;
	private String image_thumb;
	private String image_large;
	
	public Bird() {
		super();
    }

    public Bird(Parcel in) {
         //Log.i(BirdsFragment.LOGTAG, "Parcel constructor");
        
         id = in.readLong();
         name = in.readString();
         latin_name = in.readString();
         status = in.readString();
         identification = in.readString();
         diet = in.readString();
         breeding = in.readString();
         wintering_habits = in.readString();
         where_to_see = in.readString();
         conservation = in.readString();
         image_thumb = in.readString();
         image_large = in.readString();
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLatinName() {
		return latin_name;
	}

	public void setLatinName(String latin_name) {
		this.latin_name = latin_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getIdentification() {
		return identification;
	}
	
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}
	
	public String getBreeding() {
		return breeding;
	}

	public void setBreeding(String breeding) {
		this.breeding = breeding;
	}

	public String getWinteringHabits() {
		return wintering_habits;
	}

	public void setWinteringHabits(String wintering_habits) {
		this.wintering_habits = wintering_habits;
	}

	public String getWhereToSee() {
		return where_to_see;
	}

	public void setWhereToSee(String where_to_see) {
		this.where_to_see = where_to_see;
	}

	public String getConservation() {
		return conservation;
	}

	public void setConservation(String conservation) {
		this.conservation = conservation;
	}
	
	public String getImageThumb() {
		return image_thumb;
	}

	public void setImageThumb(String image_thumb) {
		this.image_thumb = image_thumb;
	}
	
	public String getImageLarge() {
		return image_large;
	}

	public void setImageLarge(String image_large) {
		this.image_large = image_large;
	}

	@Override
	public String toString() {
		return name + "\n" + latin_name + "\n";
	}

    @Override
    public int describeContents() {
         return 0;
    }
   
    @Override
    public void writeToParcel(Parcel dest, int flags) {
         //Log.i(BirdsFragment.LOGTAG, "writeToParcel");
        
         dest.writeLong(id);
         dest.writeString(name);
         dest.writeString(latin_name);
         dest.writeString(status);
         dest.writeString(identification);
         dest.writeString(diet);
         dest.writeString(breeding);
         dest.writeString(wintering_habits);
         dest.writeString(where_to_see);
         dest.writeString(conservation);
         dest.writeString(image_thumb);
         dest.writeString(image_large);
    }

    public static final Creator<Bird> CREATOR =
              new Creator<Bird>() {

         @Override
         public Bird createFromParcel(Parcel source) {
              //Log.i(BirdsFragment.LOGTAG, "createFromParcel");
              return new Bird(source);
         }

         @Override
         public Bird[] newArray(int size) {
              //Log.i(BirdsFragment.LOGTAG, "newArray");
              return new Bird[size];
         }
    };
}
