package com.carrot.catatrack.model;

/**
 * @author Philip Mathee
 * @version 1.0
 * Interface that contains all the available choices for ChoiceBoxes
 */
public interface Choices {
    String[] status = {"Awaiting 1st Surgery", "Awaiting 2nd Surgery", "Bilateral pseudophakic", "N/A"};
    String[] VAList = {"6/6", "6/9", "6/12", "6/18", "6/24", "6/36", "6/60", "CF", "HM", "LP", "NLP", "N/A"};
    String[] Lens = {"Pseudophakic", "Early cataract", "Immature cataract", "Mature cataract", "N/A"};
    String[] surgeryType = {"Blitz", "Sics", "Phaco", "N/A"};
    String[] surgPlaces = {"Manapo Regional Hospital", "National District Hospital", "Other", "N/A"};
}
