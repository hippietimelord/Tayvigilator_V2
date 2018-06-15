package com.mad.tayvigilator;

import java.util.Date;

public class Slot {
    public String role;
    public Date start;
    public Date end;
    public Date date;
    public String venue;

    public void initSlot(String role, Date start, Date end, Date date, String venue) {
        role = this.role;
        start = this.start;
        end = this.end;
        date = this.date;
        venue = this.venue;
    }
}
