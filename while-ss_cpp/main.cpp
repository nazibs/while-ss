#include <iostream>
#include <string>
#include <cctype>
using namespace std;

string INTEGER="INTEGER", PLUS="PLUS", MINUS="MINUS", MUL="MUL", DIV="DIV", SPACE="SPACE", E_O_F="EOF";

class Token{
public:
    string type, value;
    Token(string type, string value){
        this->type = type;
        this->value = value;
    }
};

class ParsingException{
public:
    ParsingException(string message){
        throw std::runtime_error(message);
    }
};

void print(string message){
    bool debugging = true;
    if(debugging)
        cout << message << endl;
}

class ASTNode{
public:
    Token token;
    ASTNode *left;
    ASTNode *right;

    ASTNode(Token value){
        token = value;
        left = nullptr;
        right = nullptr;
    }

};

class Lexer{
public:
    string text;
    int pos;
    char currentChar;

    Lexer(string text){
        this->text = text;
        this->pos = 0;
        this->currentChar = text.at(pos);
    }

    void goToNextCharacter(){
        pos++;
        if(pos > text.length()-1)
            currentChar = '#';
        else
            currentChar = text.at(pos);
    }

    void skipWhiteSpaces(){
        while(currentChar != '#' && currentChar == ' ')
            goToNextCharacter();
    }

    int getCompleteInteger(){
        string completeInteger = "";
        while(currentChar != '#' && isdigit(currentChar)){
            completeInteger = completeInteger + currentChar;
            goToNextCharacter();
        }
        return stoi(completeInteger);
    }

    Token getNextToken(){
        string text = this->text;
        if(pos > text.length()-1){
            currentChar = '#';
            return Token(E_O_F, NULL);
        }
        while(currentChar != '#'){
            string message = "Current Character: ";
            message.push_back(currentChar);
            print(message);
            if(isdigit(currentChar))
                return Token(INTEGER, "" + to_string(getCompleteInteger()));
            if(currentChar == ' '){
                skipWhiteSpaces();
                continue;
            }
            if(currentChar == '+'){
                string temp;
                temp.push_back(currentChar);
                Token token = Token(PLUS, temp);
                goToNextCharacter();
                return token;
            }
            if(currentChar == '-'){
                string temp;
                temp.push_back(currentChar);
                Token token = Token(MINUS, temp);
                goToNextCharacter();
//                Handling Negative Characters
                if(isdigit(currentChar))
                    token = Token(INTEGER, "" + to_string(-1*getCompleteInteger()));
                return token;
            }
            if(currentChar == '*'){
                string temp;
                temp.push_back(currentChar);
                Token token = Token(MUL, temp);
                goToNextCharacter();
                return token;
            }
            if(currentChar == '/'){
                string temp;
                temp.push_back(currentChar);
                Token token = Token(DIV, temp);
                goToNextCharacter();
                return token;
            }
            throw ParsingException("Error in parsing message");
        }
        return Token(E_O_F, NULL);
    }

//public int getCompleteInteger(){
//        StringBuilder completeInt = new StringBuilder();
//        while(this.currentChar != '#' && Character.isDigit(this.currentChar)) {
//            completeInt.append(this.currentChar);
//            goToNextCharacter();
//        }
////        While.print("Complete Integer: " + completeInt.toString());
//        return Integer.parseInt(completeInt.toString());
//    }


};



int main() {
    cout << "Hello, World: " << INTEGER << endl;

    Token t(INTEGER, "15");
    Token t2(INTEGER, "20");

    string temp;
    temp.push_back('S');

    cout << "Concatenated temp: " << temp << endl;



//    cout << "Token type: " << token->type << " value: " << token->value << endl;

    cout << "Token type: " << t.type << " value: " << t.value << endl;

    ParsingException("Error in Parsing");

    cout << "Token type: " << t2.type << " value: " << t2.value << endl;

    return 0;
}
