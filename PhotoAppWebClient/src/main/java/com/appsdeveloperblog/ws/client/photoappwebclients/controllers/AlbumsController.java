package com.appsdeveloperblog.ws.client.photoappwebclients.controllers;

//import java.util.Arrays;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.appsdeveloperblog.ws.client.photoappwebclients.response.AlbumRest;

@Controller
public class AlbumsController {
	
	
	private final OAuth2AuthorizedClientService oauth2ClientService;
	
	private final RestTemplate restTemplate;
	
	public AlbumsController(RestTemplate restTemplate, OAuth2AuthorizedClientService oauth2ClientService) {
		this.restTemplate=restTemplate;
		this.oauth2ClientService=oauth2ClientService;
	}

	@GetMapping("/albums")
	public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {

		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken oauthToken= (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient oauth2Client=oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId()
																					,oauthToken.getName());
		String jwtAccessToken=oauth2Client.getAccessToken().getTokenValue();
		System.out.println("The JWT Access Token is: "+jwtAccessToken);
		
		System.out.println("The principal is: " + principal);
		
		OidcIdToken idToken=principal.getIdToken();
		
		String idTokenValue=idToken.getTokenValue();

		System.out.println("The id token is: " + idTokenValue);
		
		/*String url = "http://localhost:8082/albums";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwtAccessToken);
		
		HttpEntity<List<AlbumRest>> entity = new HttpEntity<>(headers);
		
		
		ResponseEntity<List<AlbumRest>> responseEntity =  restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AlbumRest>>(){});
 
		List<AlbumRest> albums = responseEntity.getBody();		
		model.addAttribute("albums", albums);*/
		String url = "http://localhost:8082/albums";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwtAccessToken);
		
		HttpEntity<List<AlbumRest>> entity = new HttpEntity<>(headers);
		//ParameterizedTypeReference<List<AlbumRest>> albumRest = new ParameterizedTypeReference<List<AlbumRest>>() {};
		
		
		//ResponseEntity<List<AlbumRest>> responseEntity 
		ResponseEntity<List<AlbumRest>> responseEntity=  restTemplate.exchange(url, HttpMethod.GET, entity, 
				new ParameterizedTypeReference<List<AlbumRest>>(){});
		
		List<AlbumRest> albums = responseEntity.getBody();

		/*AlbumRest album1 = new AlbumRest("albumuser1", "albumId1", "albumTitle1", "albumDescription1",
				"http://localhost:8011/albums/1");

		AlbumRest album2 = new AlbumRest("albumuser2", "albumId2", "albumTitle2", "albumDescription2",
				"http://localhost:8012/albums/2");

		model.addAttribute("albums", Arrays.asList(album1, album2));*/
		model.addAttribute("albums", albums);
		return "albums";
	}
}
