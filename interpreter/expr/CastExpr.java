package interpreter.expr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import error.LanguageException;
import interpreter.type.composed.ArrayType;
import interpreter.type.composed.DictType;
import interpreter.type.primitive.BoolType;
import interpreter.type.primitive.CharType;
import interpreter.type.primitive.FloatType;
import interpreter.type.primitive.IntType;
import interpreter.type.primitive.StringType;
import interpreter.value.Value;

public class CastExpr extends Expr {

    public static enum CastOp{
        ToBoolOp,
        ToIntOp,
        ToFloatOp,
        ToCharOp,
        ToStringOp
    }

    private CastOp op;
    private Expr expr;

    public CastExpr(int line, CastOp op, Expr expr) {
        super(line);
        this.op = op;
        this.expr = expr;
        
    }

    @Override
    public Value expr() {
        Value value = expr.expr();
        Value ret = null;
        switch(op){
            case ToBoolOp:
                ret = toBoolOp(value);
                break;
            case ToIntOp:
                ret = toIntOp(value);
                break;
            case ToFloatOp:
                ret = toFloatOp(value);
                break;
            case ToCharOp:
                ret = toCharOp(value);
                break;
            case ToStringOp:
                ret = toStringOP(value);
                break;
            default:
                throw new InternalError("unreachable");
        }
        
        return ret;
    }

    private Value toStringOP(Value value) {
        //Tem que completar
         BoolType boolType = BoolType.instance();
        if (boolType.match(value.type)) {
            Boolean boolValue = (Boolean) value.data;
            String stringValue = Boolean.toString(boolValue);
            return new Value(StringType.instance(), stringValue);
        } else if (value.type instanceof IntType) {
            int intValue = (int) value.data;
            String stringValue = Integer.toString(intValue);
            return new Value(StringType.instance(), stringValue);
        } else if(value.type instanceof CharType){
            char charValue = (char) value.data;
            String stringValue = Character.toString(charValue); 
            return new Value(StringType.instance(), stringValue);
        } else if (value.type instanceof FloatType) {
            float floatValue = (float) value.data;
            String stringValue = Float.toString(floatValue); 
            return new Value(StringType.instance(), stringValue);
        } else if (value.type instanceof StringType) {
            return value;
        }else {
            throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType, value.type.toString());
        }
    }

    private Value toCharOp(Value value) {
        //Tem que completar
        return null;
    }

    private Value toFloatOp(Value value) {
        //Tem que completar
        BoolType boolType = BoolType.instance();
        if (boolType.match(value.type)) {
            boolean boolValue = (boolean) value.data;
            float floatValue;
            if (boolValue)
                floatValue = 1;
            else 
                floatValue = 0;
            return new Value(FloatType.instance(), floatValue);
        } else if (value.type instanceof IntType) {
            int intValue = (int) value.data;
            float floatValue = (float) ((float) intValue + 0.0);
            return new Value(FloatType.instance(), floatValue);
        } else if(value.type instanceof CharType){
            char charValue = (char) value.data;
            float floatValue = (float) Character.getNumericValue(charValue); 
            return new Value(FloatType.instance(), floatValue);
        } else if (value.type instanceof FloatType) {
            return value;
        } else if (value.type instanceof StringType){
            String stringValue = (String) value.data;
            float floatValue = 0;
            try {
                floatValue = Float.parseFloat(stringValue);
            } catch (Exception e) {
                throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidOperation, value.data.toString() + " is not a number.");
            }
            return new Value(FloatType.instance(), floatValue);
        }  else {
            throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType, value.type.toString());
        }
    }

    private Value toIntOp(Value value) {
        //Tem que completar
        BoolType boolType = BoolType.instance();
        if (boolType.match(value.type)) {
            boolean boolValue = (boolean) value.data;
            int intValue;
            if (boolValue)
                intValue = 1;
            else 
                intValue = 0;
            return new Value(IntType.instance(), intValue);
        } else if (value.type instanceof IntType) {
            return value;
        } else if(value.type instanceof CharType){
            char charValue = (char) value.data;
            int intValue = Character.getNumericValue(charValue); 
            return new Value(IntType.instance(), intValue);
        } else if (value.type instanceof FloatType) {
            float floatValue = (float) value.data;
            int intValue = Math.round(floatValue); 
            return new Value(IntType.instance(), intValue);
        } else if (value.type instanceof StringType){
            String stringValue = (String) value.data;
            int intValue = 0;
            try {
                intValue = Integer.parseInt(stringValue);
            } catch (Exception e) {
                throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidOperation, value.data.toString() + " is not a number.");
            }
            return new Value(IntType.instance(), intValue);
        }  else {
            throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType, value.type.toString());
        }
    }

    private Value toBoolOp(Value value) {
        //Tem que completar
        BoolType boolType = BoolType.instance();
        if (boolType.match(value.type)) {
            return value;
        } else if (value.type instanceof IntType) {
            int intValue = (int) value.data;
            boolean boolValue = intValue != 0;
            return new Value(BoolType.instance(), boolValue);
        } else if(value.type instanceof CharType){
            char charValue = (char) value.data;
            boolean boolValue = charValue != 0; 
            return new Value(BoolType.instance(), boolValue);
        } else if (value.type instanceof FloatType) {
            float floatValue = (float) value.data;
            boolean boolValue = floatValue != 0.0; 
            return new Value(BoolType.instance(), boolValue);
        } else if (value.type instanceof StringType){
            String stringValue = (String) value.data;
            boolean boolValue = !stringValue.equals("");
            return new Value(BoolType.instance(), boolValue);
        }  else {
            throw LanguageException.instance(super.getLine(), LanguageException.Error.InvalidType, value.type.toString());
        }
    }
}
