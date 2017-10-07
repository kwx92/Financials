
package business;

/*
 * @author Katelyn
 */
public class Annuity {
    public static final String AMTDESC = "Monthly Deposit";
    public static final String RESDESC = "Final Value of Annuity";
    
    private double amt, rate;
    private int term;
    private String errmsg;
    private boolean built;
    private double[] bbal, intearn, ebal;
    
    public Annuity(double a, double r, int t)
    {
        this.amt = a;
        this.rate = r;
        this.term = t;
        this.built = false;
        
        if (isValid())
        {
            calcAnnuity();
        }
    }
    
    private boolean isValid()
    {
        this.errmsg = "";
        if (this.amt <= 0)
        {
            this.errmsg += "Amount is illegal.";
        }
        if (this.rate <= 0 || this.rate > 1.0)
        {
            this.errmsg += " Rate must be between > 0 and <= 1.00 ";
        }
        if (this.term <= 0)
        {
            this.errmsg += " Term is illegal. ";
        }
        if (this.errmsg.isEmpty())
        {
            return true;
        }
        return false;
    }
    
    private void calcAnnuity()
    {
        try
        {
            bbal = new double [this.term];
            intearn = new double[this.term];
            ebal = new double[this.term];
            
            bbal[0] = 0;
            for(int i=0; i < this.term; i++)
            {
                if (i > 0)
                {
                    this.bbal[i] = this.ebal[i-1];
                }
                this.intearn[i] = (this.bbal[i] + this.amt)*(this.rate/12.0);
                this.ebal[i] = this.bbal[i] + this.intearn[i] + this.amt;
            }
            this.built = true;
        }
        catch (Exception e)
        {
            this.errmsg = "Annuity failed to build.";
            this.built = false;
        }
    }
    
    public String getErrMsg()
    {
        return this.errmsg;
    }
    
    public double getFinalValue()
    {
        if(!this.built)
        {
            calcAnnuity();
            if(!this.built)
            {
                return -1;
            }
        }
        return ebal[this.term-1];
    }
    
    public double getDeposit()
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
            calcAnnuity();
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
    
    public double getIntEarn(int mo)
    {
        if (!this.built)
        {
            calcAnnuity();
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
        return intearn[mo-1];
    }
    
    public double getEndBal(int mo)
    {
        if (!this.built)
        {
            calcAnnuity();
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
