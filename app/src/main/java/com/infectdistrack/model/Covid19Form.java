package com.infectdistrack.model;

public class Covid19Form {
    private Integer parentUserID;
    private String patientID, formID;
    private String symptoms, consulterMedecin, strucureMedecin, raisonAbsence, sabsenterDuTravail,
            combienDeJours, contactAvecPersonneSuspecte, telPersonneSuspecte, dateDernierContactPersonneSuspecte,
            niveauSocioEconomique, conditionPreDisposante, listeDesConditionsPreDisposantes,
            testCovid, typeTest, DateTest, tdr, pcr, scanner, tdrResponse, tdrDetails, pcrReponse, scannerResponse,
            statutActuel, detailVivant, dateAdmission, structureSanitaireHospitalisation,
            dateDeces, lieuDeces, dureeHospitalisation, structureSanitaireDeces;

    public Covid19Form() {
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public Integer getParentUserID() {
        return parentUserID;
    }

    public void setParentUserID(Integer parentUserID) {
        this.parentUserID = parentUserID;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getConsulterMedecin() {
        return consulterMedecin;
    }

    public void setConsulterMedecin(String consulterMedecin) {
        this.consulterMedecin = consulterMedecin;
    }

    public String getStrucureMedecin() {
        return strucureMedecin;
    }

    public void setStrucureMedecin(String strucureMedecin) {
        this.strucureMedecin = strucureMedecin;
    }

    public String getRaisonAbsence() {
        return raisonAbsence;
    }

    public void setRaisonAbsence(String raisonAbsence) {
        this.raisonAbsence = raisonAbsence;
    }

    public String getSabsenterDuTravail() {
        return sabsenterDuTravail;
    }

    public void setSabsenterDuTravail(String sabsentirDuTravail) {
        this.sabsenterDuTravail = sabsentirDuTravail;
    }

    public String getCombienDeJours() {
        return combienDeJours;
    }

    public void setCombienDeJours(String combienDeJours) {
        this.combienDeJours = combienDeJours;
    }

    public String getContactAvecPersonneSuspecte() {
        return contactAvecPersonneSuspecte;
    }

    public void setContactAvecPersonneSuspecte(String contactAvecPersonneSuspecte) {
        this.contactAvecPersonneSuspecte = contactAvecPersonneSuspecte;
    }

    public String getTelPersonneSuspecte() {
        return telPersonneSuspecte;
    }

    public void setTelPersonneSuspecte(String telPersonneSuspecte) {
        this.telPersonneSuspecte = telPersonneSuspecte;
    }

    public String getDateDernierContactPersonneSuspecte() {
        return dateDernierContactPersonneSuspecte;
    }

    public void setDateDernierContactPersonneSuspecte(String dateDernierContactPersonneSuspecte) {
        this.dateDernierContactPersonneSuspecte = dateDernierContactPersonneSuspecte;
    }

    public String getNiveauSocioEconomique() {
        return niveauSocioEconomique;
    }

    public void setNiveauSocioEconomique(String niveauSocioEconomique) {
        this.niveauSocioEconomique = niveauSocioEconomique;
    }

    public String getConditionPreDisposante() {
        return conditionPreDisposante;
    }

    public void setConditionPreDisposante(String conditionPreDisposante) {
        this.conditionPreDisposante = conditionPreDisposante;
    }

    public String getListeDesConditionsPreDisposantes() {
        return listeDesConditionsPreDisposantes;
    }

    public void setListeDesConditionsPreDisposantes(String listeDesConditionsPreDisposantes) {
        this.listeDesConditionsPreDisposantes = listeDesConditionsPreDisposantes;
    }

    public String getTestCovid() {
        return testCovid;
    }

    public void setTestCovid(String testCovid) {
        this.testCovid = testCovid;
    }

    public String getTypeTest() {
        return typeTest;
    }

    public void setTypeTest(String typeTest) {
        this.typeTest = typeTest;
    }

    public String getDateTest() {
        return DateTest;
    }

    public void setDateTest(String dateTest) {
        DateTest = dateTest;
    }

    public String getTdr() {
        return tdr;
    }

    public void setTdr(String tdr) {
        this.tdr = tdr;
    }

    public String getPcr() {
        return pcr;
    }

    public void setPcr(String pcr) {
        this.pcr = pcr;
    }

    public String getScanner() {
        return scanner;
    }

    public void setScanner(String scanner) {
        this.scanner = scanner;
    }

    public String getTdrResponse() {
        return tdrResponse;
    }

    public void setTdrResponse(String tdrResponse) {
        this.tdrResponse = tdrResponse;
    }

    public String getTdrDetails() {
        return tdrDetails;
    }

    public void setTdrDetails(String tdrDetails) {
        this.tdrDetails = tdrDetails;
    }

    public String getPcrReponse() {
        return pcrReponse;
    }

    public void setPcrReponse(String pcrReponse) {
        this.pcrReponse = pcrReponse;
    }

    public String getScannerResponse() {
        return scannerResponse;
    }

    public void setScannerResponse(String scannerResponse) {
        this.scannerResponse = scannerResponse;
    }

    public String getStatutActuel() {
        return statutActuel;
    }

    public void setStatutActuel(String statutActuel) {
        this.statutActuel = statutActuel;
    }

    public String getDetailVivant() {
        return detailVivant;
    }

    public void setDetailVivant(String detailVivant) {
        this.detailVivant = detailVivant;
    }

    public String getDateAdmission() {
        return dateAdmission;
    }

    public void setDateAdmission(String dateAdmission) {
        this.dateAdmission = dateAdmission;
    }

    public String getStructureSanitaireHospitalisation() {
        return structureSanitaireHospitalisation;
    }

    public void setStructureSanitaireHospitalisation(String structureSanitaireHospitalisation) {
        this.structureSanitaireHospitalisation = structureSanitaireHospitalisation;
    }

    public String getDateDeces() {
        return dateDeces;
    }

    public void setDateDeces(String dateDeces) {
        this.dateDeces = dateDeces;
    }

    public String getLieuDeces() {
        return lieuDeces;
    }

    public void setLieuDeces(String lieuDeces) {
        this.lieuDeces = lieuDeces;
    }

    public String getDureeHospitalisation() {
        return dureeHospitalisation;
    }

    public void setDureeHospitalisation(String dureeHospitalisation) {
        this.dureeHospitalisation = dureeHospitalisation;
    }

    public String getStructureSanitaireDeces() {
        return structureSanitaireDeces;
    }

    public void setStructureSanitaireDeces(String structureSanitaireDeces) {
        this.structureSanitaireDeces = structureSanitaireDeces;
    }

    @Override
    public String toString() {
        return "Form ID : " + getFormID() + "\n"
                + "Parent ID : " + getParentUserID() + "\n"
                + "Patient ID : " + getPatientID() + "\n"
                + "Symptômes : " + getSymptoms() + "\n"
                + "Consulter medecin : " + getConsulterMedecin() + "\n"
                + "Structure medecin : " + getStrucureMedecin() + "\n"
                + "RaisonAbsence : " + getRaisonAbsence() + "\n"
                + "Sabsenter du travail : " + getSabsenterDuTravail() + "\n"
                + "Combien de jours : " + getCombienDeJours() + "\n"
                + "Contact avec personne suspecte : " + getContactAvecPersonneSuspecte() + "\n"
                + "Tel personne suspecte : " + getTelPersonneSuspecte() + "\n"
                + "Dernier contact : " + getDateDernierContactPersonneSuspecte() + "\n"
                + "Niveau socio-économique : " + getNiveauSocioEconomique() + "\n"
                + "Conditions pré-disposantes : " + getConditionPreDisposante() + "\n"
                + "Liste de conditions pre-dispo : " + getListeDesConditionsPreDisposantes() + "\n"
                + "Test COVID : " + getTestCovid() + "\n"
                + "Type test : " + getTypeTest() + "\n"
                + "DateTest : " + getDateTest() + "\n"
                + "TDR : " + getTdr() + "\n"
                + "PCR : " + getPcr() + "\n"
                + "Scanner : " + getScanner() + "\n"
                + "TDR response : " + getTdrResponse() + "\n"
                + "TDR details : " + getTdrDetails() + "\n"
                + "PCR response : " + getPcrReponse() + "\n"
                + "Scanner response : " + getScannerResponse() + "\n"
                + "Statut actuel : " + getStatutActuel() + "\n"
                + "detailsVivant: " + getDetailVivant() + "\n"
                + "dateAdmission: " + getDateAdmission() + "\n"
                + "structureSanitaireHospitalisation : " + getStructureSanitaireHospitalisation() + "\n"
                + "dateDeces : " + getDateDeces() + "\n"
                + "lieuDeces : " + getLieuDeces() + "\n"
                + "dureeHospitalisation : " + getDureeHospitalisation() + "\n"
                + "structureSanitaireDeces : " + getStructureSanitaireDeces();
    }
}
