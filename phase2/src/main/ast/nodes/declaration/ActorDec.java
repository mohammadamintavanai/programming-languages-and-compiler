package main.ast.nodes.declaration;

import main.ast.nodes.expression.Expression;
import main.ast.nodes.expression.Identifier;
import main.utils.ConstructorDto;
import main.visitor.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class ActorDec extends Declaration {
    private Identifier name;
    private List<CustomPrimitiveDeclaration> customPrimitiveDeclarations = new ArrayList<>();
    private List<VarDeclaration> actorVars = new ArrayList<>();
    private boolean hasConstructor;
    private String constructorName;
    private List<VarDeclaration> constructorArgs = new ArrayList<>();
    private List<Handler> msgHandlers = new ArrayList<>();

    private List<List<Expression>> accessExpressions = new ArrayList<>();

    public ActorDec(Identifier _name, int _line) {
        name = _name;
        line = _line;
        hasConstructor = false;
    }
    public void addCustomPrimitive(CustomPrimitiveDeclaration customPrimitiveDeclaration){
        customPrimitiveDeclarations.add(customPrimitiveDeclaration);
    }

    public void setActorVars(ArrayList<VarDeclaration> _actorVars){
        actorVars.addAll(_actorVars);
    }

    public void setConstructor (ConstructorDto constructor){
        constructorName = constructor.name;
        hasConstructor = true;
        constructorArgs.addAll(constructor.args);
    }

    public void addHandler(Handler handler){
        msgHandlers.add(handler);
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public void setAccessExpressions(List<List<Expression>> accessExpressions) {
        this.accessExpressions.addAll(accessExpressions);
    }
}
