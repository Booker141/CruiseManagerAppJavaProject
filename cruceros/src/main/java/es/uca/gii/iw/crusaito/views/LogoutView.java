package es.uca.gii.iw.crusaito.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import es.uca.gii.iw.crusaito.common.Funciones;
import es.uca.gii.iw.crusaito.security.SecurityUtils;

@Secured({"Cliente", "Administrador", "Gerente"})
@Route(value = "Logout",layout = MainView.class)
public class LogoutView extends VerticalLayout implements BeforeEnterObserver{

	private static final long serialVersionUID = 1L;

		//Button volver = new Button("Volver a la página principal");
		@Autowired
		public LogoutView()
		{
			//add(volver);
			Funciones.notificacionAcierto("Ha cerrado la sesión correctamente");
			this.setAlignItems(Alignment.CENTER);
			
		    //Funciones.clickListener(volver, "MainView");
		}
		
		@Override
		public void beforeEnter(BeforeEnterEvent event) {
			final boolean accessGranted =
					SecurityUtils.isAccessGranted(event.getNavigationTarget());
			if(!accessGranted) {
				if(SecurityUtils.isUserLoggedIn()) {
					event.rerouteToError(AccessDeniedException.class);
				}
				else {
					event.rerouteTo(LoginView.class);
				}
			}else {
				SecurityContextHolder.clearContext();
				getUI().get().getSession().close();
			}
		}
	}


