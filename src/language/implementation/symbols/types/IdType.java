package language.implementation.symbols.types;

import language.implementation.Visitor;
import language.implementation.symbols.Type;
import language.implementation.symbols.terminals.Identifier;

public class IdType extends Type{
	private static final long serialVersionUID = 1L;

	public final Identifier id;
	
	public IdType(Identifier id) {
		this.id = id;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdType other = (IdType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
