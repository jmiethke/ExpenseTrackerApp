package com.c323proj9.jmiethke;


public class Expense {
    private String name, date, category;
    private int id;
    private  double moneySpent;

    public Expense(String name, String date, String category, double moneySpent) {
        this.name = name;
        this.date = date;
        this.category = category;
        this.moneySpent = moneySpent;
    }

    public Expense() {
    }

    /**
     * Compares this Expense to another Expense by date.
     * @param e
     * @return Returning true if this Expense is younger than Expense e.
     */
    public boolean isBefore(Expense e) {
        String eDate = e.getDate();
        if (Integer.parseInt(eDate.substring(6)) < Integer.parseInt(this.date.substring(6))) {
            return false;
        }
        // Same year
        else if (Integer.parseInt(eDate.substring(6)) == Integer.parseInt(this.date.substring(6)))  {
            if (Integer.parseInt(eDate.substring(0,2)) < Integer.parseInt(this.date.substring(0,2))) {
                return false;
            }
            // Same month
            else if (Integer.parseInt(eDate.substring(0,2)) == Integer.parseInt(this.date.substring(0,2))) {
                if (Integer.parseInt(eDate.substring(3,5)) < Integer.parseInt(this.date.substring(3,5))) {
                    return false;
                }
                // Same day or before.
                else  {
                    return true;
                }
            } else {
                return true;
            }
        }
        else {
            return true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id){ this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(double moneySpent) {
        this.moneySpent = moneySpent;
    }
}
