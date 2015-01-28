package th.ac.tu.siit.its333.lab2exercise1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // expr = the current string to be calculated
    StringBuffer expr;
    int memory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = new StringBuffer();
        updateExprDisplay();
    }

    public void updateExprDisplay() {
        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(expr.toString());
    }



    public void recalculate() {
        int result = 0;
        //Calculate the expression and display the output
        //Split expr into numbers and operators
        //e.g. 123+45/3 --> ["123", "+", "45", "/", "3"]
        //reference: http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
        String e = expr.toString();
        String[] tokens = e.split("((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");
        result = Integer.parseInt(tokens[0]);
        for(int i =0;i<tokens.length-1;i++) {
            if (i % 2 == 1) {
                char x = tokens[i].charAt(0);
                if(x=='+')
                {
                    result += Integer.parseInt(tokens[i + 1]);
                }
                else if (x == '-')
                {
                    result -= Integer.parseInt(tokens[i + 1]);
                }
                else if (x == '*')
                {
                    result *= Integer.parseInt(tokens[i + 1]);
                }
                else if (x == '/')
                {
                    result /= Integer.parseInt(tokens[i + 1]);
                }

            }
        }
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(Integer.toString(result));
     }

    public void digitClicked(View v) {
        //d = the label of the digit button
        String d = ((TextView)v).getText().toString();
        //append the clicked digit to expr
        expr.append(d);
        //update tvExpr
        updateExprDisplay();
        //calculate the result if possible and update tvAns
        recalculate();
    }

    public void operatorClicked(View v) {
        String o = ((TextView)v).getText().toString();
        //IF the last character in expr is not an operator and expr is not "",
        // or use if(!expr.equals("") or expr.length>0
        //THEN append the clicked operator and updateExprDisplay,
        // ELSE do nothing
    if(!expr.toString().isEmpty() && !isOperand(expr.charAt(expr.length() -1 )) )
        {
           expr.append(o);
           updateExprDisplay();
           //recalculate();
        }else{
          }
    }

    private boolean isOperand(char c){
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public void equalClicked(View v) {

        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        String ans = tvAns.getText().toString();
        tvAns.setText("0");
        expr = new StringBuffer();
        expr.append(ans);
        updateExprDisplay();

    }

    public void ACClicked(View v) {
        //Clear expr and updateExprDisplay
        expr = new StringBuffer();
        updateExprDisplay();
        //Display a toast that the value is cleared
        Toast t = Toast.makeText(this.getApplicationContext(),
                "All cleared", Toast.LENGTH_SHORT);
        TextView tvAns= (TextView)findViewById(R.id.tvAns);
        tvAns.setText("0");
        t.show();
    }

    public void BSClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        if (expr.length() > 0) {
            expr.deleteCharAt(expr.length()-1);
            updateExprDisplay();
            recalculate();
        }
    }

    public void MClicked (View v){
        int id = v.getId();
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        Toast t;
        switch (id){
            case R.id.madd:
                memory += Integer.parseInt(tvAns.getText().toString());
                t = Toast.makeText(this.getApplicationContext(),"Memory is "+ memory
                ,Toast.LENGTH_SHORT);
                t.show();
                break;
            case R.id.msub:
                memory -= Integer.parseInt(tvAns.getText().toString());
                t = Toast.makeText(this.getApplicationContext(),"Memory is "+ memory
                        ,Toast.LENGTH_SHORT);
                t.show();
                break;
            case R.id.mr:
                TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
                tvExpr.setText(memory+"");
                tvAns.setText(memory+"");
                t = Toast.makeText(this.getApplicationContext(),"Memory is "+ memory
                        ,Toast.LENGTH_SHORT);
                t.show();
                break;
            case R.id.mc:
                memory = 0;
                // expr = new StringBuffer(memory);
                tvAns.setText("0");
                t = Toast.makeText(this.getApplicationContext(),"Memory is cleared to " + memory
                        ,Toast.LENGTH_SHORT);
                t.show();
                break;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
