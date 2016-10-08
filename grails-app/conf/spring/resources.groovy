import couple.shopping.Couple
import couple.shopping.infra.CoupleConfirmationNotifier
import couple.shopping.infra.mail.EmailNotifier
import couple.shopping.mock.EmailNotifierMock
import grails.converters.JSON
import grails.util.Environment

// Place your Spring DSL code here
beans = {
	JSON.registerObjectMarshaller(Couple, { couple ->
		def jCouple = [
			id: couple.id,
			name: couple.name,
			users: []
		]
		couple.users.each{
			jCouple.users << [
				id: it.id,
				name: it.name,
				email: it.email
			]
		}
		jCouple
	})

	if(Environment.current.name == "test"){
		emailNotifier(EmailNotifierMock)
	} else {
		emailNotifier(EmailNotifier)
	}
	coupleConfirmationNotifier(CoupleConfirmationNotifier)
}
