package us.ledge.line.sdk.sdk.vos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User data.
 * @author Wijnand
 */
public class UserDataVo implements Parcelable {

    public int loanAmount;

    public String firstName;
    public String lastName;
    public String emailAddress;
    public long phoneNumber;

    public String address;
    public String apartmentNumber; // Or unit.
    public String city;
    public String state;
    public int zip;

    public int income;

    public static final String USER_DATA_KEY = "us.ledge.line.sdk.sdk.vos.UserData";
    public static final Creator<UserDataVo> CREATOR = new Creator<UserDataVo>() {
        /** {@inheritDoc} */
        @Override
        public UserDataVo createFromParcel(Parcel in) {
            return new UserDataVo(in);
        }

        /** {@inheritDoc} */
        @Override
        public UserDataVo[] newArray(int size) {
            return new UserDataVo[size];
        }
    };

    /**
     * Creates a new {@link UserDataVo} instance.
     */
    public UserDataVo() { /* Do nothing. */ }

    /**
     * Creates a new {@link UserDataVo} instance.
     * @param in The {@link Parcel} to read the initial data from.
     */
    public UserDataVo(Parcel in) {
        loanAmount = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        emailAddress = in.readString();
        phoneNumber = in.readLong();
        address = in.readString();
        apartmentNumber = in.readString();
        city = in.readString();
        state = in.readString();
        zip = in.readInt();
        income = in.readInt();
    }

    /** {@inheritDoc} */
    @Override
    public int describeContents() {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(loanAmount);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(emailAddress);
        dest.writeLong(phoneNumber);
        dest.writeString(address);
        dest.writeString(apartmentNumber);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeInt(zip);
        dest.writeInt(income);
    }
}
