package es.uca.gii.iw.crusaito.views;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import es.uca.gii.iw.crusaito.common.Funciones;
import es.uca.gii.iw.crusaito.security.SecurityUtils;
import es.uca.gii.iw.crusaito.spring.MessageBean;

@Route("MainView")
//Para establecer esta ruta por defecto
//@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(name = "Project Base for Crusaito with Spring", shortName = "Crusaito")
public class MainView extends AppLayout implements BeforeEnterObserver {
	
	private static final long serialVersionUID = 1L; // para evitar el warning del serial

	private Tabs tabs = new Tabs();
	private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();
	
	public MainView(@Autowired MessageBean bean) {
		getElement().setAttribute("theme", "dark"); // aplicar tema oscuro
		
		//Añade logo de la pagina
		Image logo = new Image("frontend/img/logo2.png", "logoweb");
	    logo.setHeight("44px");
	    
	    if(SecurityUtils.isUserLoggedIn()) {
	    	//Boton para cerrar sesion
	    	Button volver = new Button("Cerrar Sesión");
	    	volver.getStyle().set("margin-right", "0");
	    	volver.addClickListener(cerrar -> {
	    		SecurityContextHolder.clearContext();
	    		getUI().get().getSession().close();
	    		getUI().get().getPage().reload();
	    	});
	    	addToNavbar(new DrawerToggle(), logo, volver);
	    }else {
	    	
	    	addToNavbar(new DrawerToggle(), logo);
	    }
	    	addMenuTab("Inicio", DefaultView.class);
		
	    	if(SecurityUtils.isUserLoggedIn()) {
			
	    		Funciones.notificacionAcierto("Bienvenid@ " + SecurityUtils.currentUsername());
			
	    		if(SecurityUtils.hasRole("Cliente")) {
	    			addMenuTab("Gestionar mis reservas", MisReservasView.class);
	    			addMenuTab("Servicios", ServiciosView.class);
	    		}

	    		if(SecurityUtils.hasRole("Admin")){
	    			addMenuTab("Gestionar barcos", AdminListaBarcosView.class);
	    			addMenuTab("Gestionar ciudades", AdminListaCiudadesView.class);
	    			addMenuTab("Gestionar cruceros", AdminListaCrucerosView.class);
	    			addMenuTab("Gestionar servicios", AdminListaServiciosView.class);
	    			addMenuTab("Gestionar usuarios", AdminListaUsuariosView.class);
	    		}
			
	    		if(SecurityUtils.hasRole("Gerente")) {
	    			addMenuTab("Estadisticas", EstadisticasView.class);
	    		}
	    		/*
				addMenuTab("Mi perfil", PerfilView.class);
				Tab tab = new Tab(new RouterLink("Cerrar sesion",LogoutView.class));
				tab.addAttachListener(e -> {

				});
				navigationTargetToTab.put(LogoutView.class,tab);
				tabs.add(tab);*/
	    		//addMenuTab("Cerrar sesión", LogoutView.class);
	    		addMenuTab("Registrar", RegisterView.class);
			
	    	} 
	    	else {

	    		addMenuTab("Iniciar sesión", LoginView.class);
	    		addMenuTab("Registrar", RegisterView.class);
			
	    	}
		
	    	tabs.setOrientation(Tabs.Orientation.VERTICAL);
	    	addToDrawer(tabs);
		
	    	H2 mensaje = new H2("Bienvenido a la aplicación de Crusaito");
	    	VerticalLayout Bienvenida = new VerticalLayout(mensaje);

	    	setContent(Bienvenida);
	    	//Footer footer = new Footer();	//no funciona en el mainview
	    	//add(footer);
		
	    } 


	private void addMenuTab(String label, Class<? extends Component> target) {
		Tab tab = new Tab(new RouterLink(label,target));
		navigationTargetToTab.put(target,tab);
		tabs.add(tab);
	}

	@Override
    public void beforeEnter(BeforeEnterEvent event) {
        tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
    }
	
	/**
	 * changeTheme Crea un boton para intercambiar entre tema claro y oscuro
	 */
	public Button changeTheme() {

		Button toggleButton = new Button("Cambiar Tema", click -> {
			ThemeList themeList = UI.getCurrent().getElement().getThemeList(); //

			if (themeList.contains(Lumo.DARK)) { //
				themeList.remove(Lumo.DARK);
			} else {
				themeList.add(Lumo.DARK);
			}
		});
		return toggleButton;
	}

}
