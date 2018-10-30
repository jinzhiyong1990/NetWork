package com.example.zhiyong.network;

import java.util.List;

public class LessonResult {

    private int mStatus;
    private List<Lesson> mLessons;

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public List<Lesson> getmLessons() {
        return mLessons;
    }

    public void setmLessons(List<Lesson> mLessons) {
        this.mLessons = mLessons;
    }

    @Override
    public String toString() {
        return "LessonResult{" +
                "mStatus=" + mStatus +
                ", mLessons=" + mLessons +
                '}';
    }

    public static class Lesson{


        private int mID;
        private int mLearnerNumber;
        private String mName;
        private String mSmallPictureURL;
        private String mBigPictureURL;
        private String mDescription;



        public int getmID() {
            return mID;
        }

        public void setmID(int mID) {
            this.mID = mID;
        }

        public int getmLearnerNumber() {
            return mLearnerNumber;
        }

        public void setmLearnerNumber(int mLearnerNumber) {
            this.mLearnerNumber = mLearnerNumber;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmSmallPictureURL() {
            return mSmallPictureURL;
        }

        public void setmSmallPictureURL(String mSmallPictureURL) {
            this.mSmallPictureURL = mSmallPictureURL;
        }

        public String getmBigPictureURL() {
            return mBigPictureURL;
        }

        public void setmBigPictureURL(String mBigPictureURL) {
            this.mBigPictureURL = mBigPictureURL;
        }

        public String getmDescription() {
            return mDescription;
        }

        public void setmDescription(String mDescription) {
            this.mDescription = mDescription;
        }

        @Override
        public String toString() {
            return "Lesson{" +
                    "mID=" + mID +
                    ", mLearnerNumber=" + mLearnerNumber +
                    ", mName='" + mName + '\'' +
                    ", mSmallPictureURL='" + mSmallPictureURL + '\'' +
                    ", mBigPictureURL='" + mBigPictureURL + '\'' +
                    ", mDescription='" + mDescription + '\'' +
                    '}';
        }
    }


}
