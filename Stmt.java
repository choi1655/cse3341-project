//Stmt is an interface so we can take advantage of some polymorphism in StmtSeq
interface Stmt {
	void parse();
	void semantic();
	void print(int indent);
	void execute(MemoryType memType);
}