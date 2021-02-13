package com.revature.testmodels;

import com.revature.annotations.Column;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Table;

@Table(tableName = "bands")
public class Band {

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

    public Band() {
        super();
    }

    public Band(String bandName, String vocalist, String leadGuitarist, String rhythmGuitarist, String bassist) {
        this.bandName = bandName;
        this.vocalist = vocalist;
        this.leadGuitarist = leadGuitarist;
        this.rhythmGuitarist = rhythmGuitarist;
        this.bassist = bassist;
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

    @Override
    public String toString() {
        return "Band{" +
                "bandName='" + bandName + '\'' +
                ", vocalist='" + vocalist + '\'' +
                ", leadGuitarist='" + leadGuitarist + '\'' +
                ", rhythmGuitarist='" + rhythmGuitarist + '\'' +
                ", bassist='" + bassist + '\'' +
                '}';
    }
}
