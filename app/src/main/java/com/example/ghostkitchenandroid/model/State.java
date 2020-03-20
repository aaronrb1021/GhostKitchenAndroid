package com.example.ghostkitchenandroid.model;

public enum State {
    SELECT_STATE, AK, AL, AR, AS, AZ, CA, CO, CT, DC, DE, FL, GA, GU, HI, IA, ID, IL, IN, KS, KY, LA, MA, MD, ME, MI, MN, MO, MP, MS, MT, NC, ND, NE, NH, NJ, NM, NV, NY, OH, OK, OR, PA, PR, RI, SC, SD, TN, TX, UM, UT, VA, VI, VT, WA, WI, WV, WY;


    @Override
    public String toString() {
        if (this == SELECT_STATE)
            return "Select State";

        return super.toString();
    }
}
