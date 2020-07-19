package com.infectdistrack.model;

public class Covid19Form {
    private Integer parentUserID;
    private String patientID, formID;
    private String symptoms, consulterMedecin, strucureMedecin, raisonAbsence, sabsenterDuTravail,
            combienDeJours, contactAvecPersonneSuspecte, telPersonneSuspecte, dateDernierContactPersonneSuspecte,
            niveauSocioEconomique, conditionPreDisposante, listeDesConditionsPreDisposantes;

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

    @Override
    public String toString() {
        return "Form ID : " + getFormID() + "\n"
                + "Parent ID : " + getParentUserID() + "\n"
                + "Patient ID : " + getPatientID() + "\n"
                + "Symptômes : " + getSymptoms() + "\n"
                + "Consulter medecin : " + getConsulterMedecin() + "\n"
                + "Structure medecin : " + getStrucureMedecin() + "\n"
                + "RaisonAbsence : " + getRaisonAbsence() + "\n"
                + "Sabsentir du travail : " + getSabsenterDuTravail() + "\n"
                + "Combien de jours : " + getCombienDeJours() + "\n"
                + "Contact avec personne suspecte : " + getContactAvecPersonneSuspecte() + "\n"
                + "Tel personne suspecte : " + getTelPersonneSuspecte() + "\n"
                + "Dernier contact : " + getDateDernierContactPersonneSuspecte() + "\n"
                + "Niveau socio-économique : " + getNiveauSocioEconomique() + "\n"
                + "Conditions pré-disposantes : " + getConditionPreDisposante() + "\n"
                + "Liste de conditions pre-dispo : " + getListeDesConditionsPreDisposantes();
    }
}
