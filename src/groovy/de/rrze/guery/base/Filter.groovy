package de.rrze.guery.base

import de.rrze.guery.operator.Operator;
import grails.converters.JSON

/**
 * Generic filter representation 
 * 
 * @author unrza249
 *
 */
class Filter {

	String 					id //required
	String 					field
	String 					label
	String 					type = 'string' // required
	String 					input
	Boolean 				multiple
	String 					placeholder
	Boolean 				vertical
	Collection<Operator>	operators = []
	Map<String,String>		values = [:]
    String                  plugin
    Map<String,String>      plugin_config = [:]
    String                  onAfterCreateRuleInput
    String                  onAfterSetValue

	def Filter() {}
	
	def add(Operator o) {
		operators << o
	}
	
	
	def flatten() {
		def ret = [:]
		
		putIfNotEmpty(ret,"id")
		putIfNotEmpty(ret,"field")
		putIfNotEmpty(ret,"label")
		putIfNotEmpty(ret,"type")
		putIfNotEmpty(ret,"input")
		putIfNotEmpty(ret,"multiple")
		putIfNotEmpty(ret,"placeholder")
		putIfNotEmpty(ret,"vertical")
		putIfNotEmpty(ret,"values")
        putIfNotEmpty(ret,"plugin")
        putIfNotEmpty(ret,"plugin_config")
        putIfNotEmpty(ret,"onAfterCreateRuleInput")

        if (this.plugin?.contains("selectize") && !this.onAfterSetValue) {
            this.onAfterSetValue = "function(\$rule, value) {" +
                    "var selectize = \$rule.find('.rule-value-container input')[0].selectize;" +
                    "selectize.setValue(value);" +
                    "}"
        }
        putIfNotEmpty(ret,"onAfterSetValue")

		// handle operators
		def flatOperators = []
		this.operators.each {
			flatOperators << it.type
		}
		if(flatOperators) ret.put('operators', flatOperators)
		
		
		ret
	}
		
	
	private putIfNotEmpty(Map map, String fieldName) {
		def value = this."${fieldName}"
		if (value) {
			map.put(fieldName, value)
		}
		map
	}
}
