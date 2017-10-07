
package business;

/*
 * @author Katelyn
 */
public class Loan {
    public static final String AMTDESC = "Loan Amount";
    public static final String RESDESC = "Monthly Payment";
    
    private double amt, rate, mopmt;
    private int term;
    private String errmsg;
    private boolean built;
    private double[] bbal, intchg, ebal;
    
    public Loan(double a, double r, int t)
    {
        this.amt = a;
        this.rate = r;
        this.term = t;
        this.built = false;
        
        if(isValid())
        {
            calcLoan();
        }
    }
    
    private boolean isValid()
    {
        this.errmsg = "";
        if(this.amt <= 0)
        {
            this.errmsg += "Amount is illegal";
        }
        if (this.rate <= 0 || this.rate > 1.0)
        {
            this.errmsg += "rate must be > 0, and <= 1.00";
        }
        if(this.term <= 0)
        {
            this.errmsg += " Term is illegal";
        }
        if (this.errmsg.isEmpty())
        {
            return true;
        }
        return false;
    }
    
    private void calcLoan()
    {
        this.errmsg = "";
        try
        {
            bbal = new double[this. term];
            intchg = new double[this.term];
            ebal = new double[this.term];
            
            //calculate monthly payment
            double morate = this.rate / 12.0;
            double denom = Math.pow((1+morate), this.term) - 1;
            this.mopmt = (morate + morate/denom) * this.amt;
            
            bbal[0] = this.amt;
            for(int i=0; i < this.term; i++)
            {
                if (i > 0)
                {
                    this.bbal[i] = this.ebal [i-1];
                }
                this.intchg[i] = this.bbal[i] * (this.rate/12.0);
                this.ebal[i] = this.bbal[i] + this.intchg[i] - this.mopmt;
            }
            this.built = true;
        }
        catch (Exception e)
        {
            this.errmsg = "Unable to build loan.";
            this.built = false;
        }
    }
    
    public String getErrMsg()
    {
        return this.errmsg;
    }
    
    public double getMoPmt()
    {
        if (!this.built)
        {
            calcLoan();
            if (!this.built)
            {
                return -1;
            }
        }
        return this.mopmt; 
    }
    
    public double getAmount()
    {
        return this.amt;
    }
    
    public double getRate()
    {
        return this.rate;
    }
        
    public int getTerm()
    {
        return this.term;
    }
    
    public double getBegBal(int mo)
    {
        if (!this.built)
        {
            calcLoan();
            if (!this.built)
            {
                return -1;
            }
        }
        if (mo <= 0 || mo > this.term)
        {
            this.errmsg = "Illegal month requested";
            return -1;
        }
        return bbal[mo-1];
    }
    
    public double getIntChg(int mo)
    {
        if (!this.built)
        {
            calcLoan();
            if (!this.built)
            {
                return -1;
            }
        }
        if (mo <= 0 || mo > this.term)
        {
            this.errmsg = "Illegal month requested";
            return -1;
        }
        return intchg[mo-1];
    }
    
    public double getEndBal(int mo)
    {
        if (!this.built)
        {
            calcLoan();
            if (!this.built)
            {
                return -1;
            }
        }
        if (mo <= 0 || mo > this.term)
        {
            this.errmsg = "Illegal month requested";
            return -1;
        }
        return ebal[mo-1];
    }
}
