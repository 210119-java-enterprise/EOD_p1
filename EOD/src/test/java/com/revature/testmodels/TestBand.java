package com.revature.testmodels;

import com.revature.annotations.Column;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Table;

/**
 * This is a test class to see if the sql statements are built correctly
 */
@Table(tableName = "bands")
public class TestBand {

    @PrimaryKey(columnName = "band_name")
    private String bandName;
    @Column(columnName = "vocalist")
    private String vocalist;
    @Column(columnName = "lead_guitarist")
    private String leadGuitarist;
    @Column(columnName = "rhythm_guitarist")
    private String rhythmGuitarist;
    @Column(columnName = "bassist")
    private String bassist;
    @Column(columnName = "drummer")
    private String drummer;

    public TestBand() {
        super();
    }

    public TestBand(String bandName, String vocalist, String leadGuitarist, String rhythmGuitarist, String bassist, String drummer) {
        this.bandName = bandName;
        this.vocalist = vocalist;
        this.leadGuitarist = leadGuitarist;
        this.rhythmGuitarist = rhythmGuitarist;
        this.bassist = bassist;
        this.drummer = drummer;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getVocalist() {
        return vocalist;
    }

    public void setVocalist(String vocalist) {
        this.vocalist = vocalist;
    }

    public String getLeadGuitarist() {
        return leadGuitarist;
    }

    public void setLeadGuitarist(String leadGuitarist) {
        this.leadGuitarist = leadGuitarist;
    }

    public String getRhythmGuitarist() {
        return rhythmGuitarist;
    }

    public void setRhythmGuitarist(String rhythmGuitarist) {
        this.rhythmGuitarist = rhythmGuitarist;
    }

    public String getBassist() {
        return bassist;
    }

    public void setBassist(String bassist) {
        this.bassist = bassist;
    }

    public String getDrummer() {
        return drummer;
    }

    public void setDrummer(String drummer) {
        this.drummer = drummer;
    }

    @Override
    public String toString() {
        return "TestBand{" +
                "bandName='" + bandName + '\'' +
                ", vocalist='" + vocalist + '\'' +
                ", leadGuitarist='" + leadGuitarist + '\'' +
                ", rhythmGuitarist='" + rhythmGuitarist + '\'' +
                ", bassist='" + bassist + '\'' +
                ", drummer='" + drummer + '\'' +
                '}';
    }
}
