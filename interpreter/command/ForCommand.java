package interpreter.command;

import error.LanguageException;
import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.value.Value;
import interpreter.type.primitive.StringType;

public class ForCommand extends Command {

    private Variable var;
    private Expr expr;
    private Command cmds;

    public ForCommand(int line, Variable var, Expr expr, Command cmds) {
        super(line);
        this.var = var;
        this.expr = expr;
        this.cmds = cmds;
    }

    @Override
    public void execute() {
        //não esta funcionando
        Value iterable = expr.expr();
        //System.out.println(iterable.data);
        //System.out.println(iterable.type);
        if (!(iterable.data instanceof Iterable<?>) && !(iterable.data instanceof String) ) {
            throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType, "Iterable expected");
        }
        if (!(iterable.data instanceof String)){
            for (Object item : (Iterable<?>) iterable.data) {
                var.setValue(new Value(var.getType(), item));
                cmds.execute();
            }
        } else {
            String stringData = (String) iterable.data;
            for (char ch : stringData.toCharArray()) {
                var.setValue(new Value(var.getType(), ch));
                cmds.execute();
                }
        }
        
    }
    //     Value initialValue = expr.expr(); 
    //     BoolType boolType = BoolType.instance();

    //     if (!boolType.match(initialValue.type)) {
    //         throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType, initialValue.type.toString());
    //     }

    //     while (true) {
    //         Value conditionValue = expr.expr(); 
    //         if (!boolType.match(conditionValue.type)) {
    //             throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType, conditionValue.type.toString());
    //         }

    //         boolean b = (Boolean) conditionValue.data;
    //         if (!b) {
    //             break; 
    //         }

    //         cmds.execute(); 

            

    //         conditionValue = expr.expr();
    //         if (!boolType.match(conditionValue.type)) {
    //             throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType, conditionValue.type.toString());
    //         }

    //         b = (Boolean) conditionValue.data;
    //         if (!b) {
    //             break; 
    //         }
    //     }
    // }
}
