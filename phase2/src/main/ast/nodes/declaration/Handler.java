package main.ast.nodes.declaration;

import main.ast.nodes.expression.Expression;
import main.ast.nodes.expression.Identifier;
import main.ast.nodes.statements.ExpressionStatement;
import main.ast.nodes.statements.Statement;

import java.util.ArrayList;
import java.util.List;

public abstract class Handler extends Declaration{
    private Identifier name ;

    public void setName(Identifier _name) {
        this.name = _name;
    }

    private List<VarDeclaration> args = new ArrayList<>();
    private List<Statement> body = new ArrayList<>();
    private List<Expression> authorizationExpressions = new ArrayList<>();

    public void setArgs (List<VarDeclaration> _args){
        args.addAll(_args);
    }

    public void setBody(List<Statement> _body){
        body.addAll(_body);
    }

    public void setAuthorizationExpressions(List<Expression> _authorizationExpressions) {
        authorizationExpressions.addAll(_authorizationExpressions);
    }

    public Identifier getName() {
        return name;
    }

    public List<Statement> getBody() {
        return body;
    }
}
