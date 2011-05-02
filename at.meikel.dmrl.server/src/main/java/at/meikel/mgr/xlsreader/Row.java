package at.meikel.mgr.xlsreader;

import java.util.Collections;
import java.util.Hashtable;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

public class Row {

	private Hashtable<Integer, Object> columns = new Hashtable<Integer, Object>();
	private StringBuilder toString = new StringBuilder();
	private String asString = null;

	public void setColumnValue(int columnIndex, Object value) {
		columns.put(new Integer(columnIndex), value);
		if (value != null) {
			if (toString.length() > 0) {
				toString.append(";");
			}
			toString.append(value);
		}
	}

	public int getMinColumnIndex() {
		return Collections.min(columns.keySet());
	}

	public int getMaxColumnIndex() {
		return Collections.max(columns.keySet());
	}

	public SortedSet<Integer> getColomnIndices() {
		return new TreeSet<Integer>(columns.keySet());
	}

	public Object getColumnValue(int columnIndex) {
		return columns.get(new Integer(columnIndex));
	}

	public Object getNthColumnValue(int columnIndex) {
		Vector<Integer> keys = new Vector<Integer>(columns.keySet());
		if (keys.isEmpty()) {
			return null;
		}

		if (columnIndex >= keys.size()) {
			return null;
		}

		Collections.sort(keys);
		return columns.get(keys.get(columnIndex));
	}

	@Override
	public String toString() {
		if (asString == null) {
			toString.insert(0, "[Row (");
			toString.append(")]");
			asString = toString.toString();
		}
		return asString;
	}

}
