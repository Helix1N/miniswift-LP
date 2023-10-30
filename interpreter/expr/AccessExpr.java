package interpreter.expr;

import java.util.List;
import java.util.Map;

import error.LanguageException;
import interpreter.type.Type;
import interpreter.type.Type.Category;
import interpreter.type.composed.ArrayType;
import interpreter.type.composed.DictType;
import interpreter.value.Value;

public class AccessExpr extends SetExpr {
    private SetExpr base;
    private Expr index;

    public AccessExpr(int line, SetExpr base, Expr index) {
        super(line);
        this.base = base;
        this.index = index;
    }

    @Override
    public Value expr() {
        /*Type baseType = base.expr().type;
        ArrayType arrayType = ArrayType.instance();
        if(baseType.match(arrayType)){
            ArrayType arrayType2 = (ArrayType) baseType;
            Type innerType = arrayType2.getInnerType();
            List<?> listValue = new ArrayList(base.expr().data);
            return new Value(innerType, listValue[index]);
        }*/
        Value value = base.expr();

        if (Category.Array == value.type.getCategory() || Category.String == value.type.getCategory()) {

            List<Value> elements = (List<Value>) value.data;

            int position = (int) index.expr().data;
            Value element = elements.get(position);
            return new Value(element.type, element);
        } else if (Category.Dict == value.type.getCategory()) {

            List<DictItem> listDictItems = (List<DictItem>) value.data;
            System.out.println("entrouDictAccess");
            for(DictItem item : listDictItems){
                System.out.println("Key: " + item.getKey().expr().data.toString());
                System.out.println("Value: " + item.getValue().expr().data.toString());
                if(item.getKey().expr().data == index.expr().data){
                    Type typeValue = item.getValue().expr().type;
                    return new Value(typeValue, item.getValue().expr());
                }
            }
            System.out.println("vai dar erro Access");
            throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidOperation,index.expr().data.toString() + " not found in Dict.");
           
        } else {
            throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType,
                    value.type.toString() + "Access Error1");
        }
        
    }

    @Override
    public void setValue(Value value) {
        Value value1 = base.expr();
        System.out.println(value.data);
        System.out.println("^setValue do Access");
        List<DictItem> teste = (List<DictItem>) value1.data;
        System.out.println(teste.size());
        System.out.println("^Tamanho da base");
        if (Category.Array == value1.type.getCategory() || Category.String == value1.type.getCategory()) {

            List<Value> elements = (List<Value>) value1.data;
            int position = (int) index.expr().data;
            elements.set(position, value);

        } else if (Category.Dict == value1.type.getCategory()) {
            List<DictItem> listDictItems = (List<DictItem>) value1.data;
            //DictType type = (DictType) value.type;
            System.out.println("entrouDictAccess");
            for(DictItem item : listDictItems){
                if(item.getKey() == index.expr().data){
                    DictItem dictItemOld = (DictItem) item.getKey().expr().data;
                    listDictItems.remove(dictItemOld);
                    Expr keyExpr = item.getKey();
                    DictItem dictItem = new DictItem(keyExpr,null);
                    Expr exprVal = new ConstExpr(0,value);
                    dictItem.setValue(exprVal);
                    listDictItems.add(dictItem);

                }
            }
            /*Map<Expr, Expr> map = (Map<Expr, Expr>) value1.data;
            DictType type = (DictType) value1.type;
            DictItem item = (DictItem) value.data;

            if (type.getCategory() == value.type.getCategory()) {
                map.replace(item.key, item.value);
                base = (SetExpr) map;
            } else {
                throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType,
                        value.type.toString() + "AccessError2");
            }*/
        } else {
            throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType,
                    value.type.toString() + "AccessError3");
        }

    }
    
}
