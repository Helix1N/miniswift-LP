package interpreter.expr;

import interpreter.type.Type;
import java.util.HashMap;
import java.util.List;

import interpreter.type.composed.DictType;
import interpreter.value.Value;

public class DictExpr extends Expr{
    private DictType type;
    private List<DictItem> items;

    public DictExpr(int line, DictType type, List<DictItem> items) {
        super(line);
        this.type = type;
        this.items = items;
    }

    @Override
    public Value expr() {
        HashMap<Value, Value> dictMap = new HashMap<>();
        for (DictItem item : items) {
            Value key = item.key.expr();     
            Value value = item.value.expr(); 
            dictMap.put(key, value);
        }
        //Possivelmente errado
        return new Value(type, dictMap);
    }
    
    public Type getType(){
        return this.type;
    }
}
