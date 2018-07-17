package tims.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/musicPlayer/*")
public class PlaylistBean {

	@Autowired
	private SqlSessionTemplate sqlMap;

	@RequestMapping("/Playlist.com")
	public String Playlist(HttpSession session, HttpServletRequest request) throws Exception {
		String roomMemId = (String)session.getAttribute("roomMemId");
		String[] roomMemId2 = roomMemId.split("｀");
		
		int oc_num = Integer.parseInt(roomMemId2[0]);
		
		List<Map<String, Object>> playList = sqlMap.selectList("selectPlaylist", oc_num);

		
		if(playList.size() > 0) {
			String videoId;

			String title;

			int duration;

			int hour, minute, second;

			String duration_HMS;

			int num;

			String str_hour = "00:";

			String str_minute = "00:";

			String str_second = "00";
			
			Map<String, Object> list = playList.get(0);

			videoId = (String) list.get("pl_videoId");

			title = (String) list.get("pl_title");

			duration = Integer.parseInt(String.valueOf(list.get("pl_duration")));

			if (duration >= 3600) {

				hour = duration / 3600;

				minute = (duration % 3600) / 60;

				second = (duration % 3600) % 60;

				if (hour > 0) {

					if (hour >= 10) {

						str_hour = hour + ":";
					} else if (hour < 10) {

						str_hour = "0" + hour + ":";
					}
				}

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 60) {

				minute = duration / 60;

				second = duration % 60;

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 0) {

				second = duration;

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			}

			num = Integer.parseInt(String.valueOf(list.get("m_num")));

			String m_name = sqlMap.selectOne("chating.selectM_name", num);
			
			request.setAttribute("m_name", m_name);
			
			request.setAttribute("playList", playList);

			request.setAttribute("videoId", videoId);

			request.setAttribute("title", title);

			request.setAttribute("duration_Sec", duration);

			request.setAttribute("num", num);

			System.out.println("현재 트랙: 1번 // 전체 곡 수: " + playList.size() + "개");
			
		}

		request.setAttribute("oc_num", oc_num);
		
		return "/musicPlayer/Playlist";
	}

	@RequestMapping("/Searchlist.com")
	public String searchlist(HttpServletRequest request) throws Exception {

		ArrayList duration_HMSList = new ArrayList();

		ArrayList duration_SecList = new ArrayList();

		ArrayList titleList = new ArrayList();

		ArrayList videoIdList = new ArrayList();

		V3Search v3Search = new V3Search();

		V3Videos v3Videos = new V3Videos();

		String search = request.getParameter("search").trim();

		String result = v3Search.getData(search);

		JSONParser parser = new JSONParser();

		Map<String, Object> map = (Map<String, Object>) parser.parse(result);

		List<Map<String, Object>> itemsList = (List<Map<String, Object>>) map.get("items");

		for (int i = 0; i < itemsList.size(); i++) {

			Map<String, Object> items = itemsList.get(i);

			Map<String, Object> id = (Map<String, Object>) items.get("id");

			String videoId = (String) id.get("videoId");

			videoIdList.add(videoId);

			Map<String, Object> snippet = (Map<String, Object>) items.get("snippet");

			String title = (String) snippet.get("title");

			titleList.add(title);

			Map<String, Object> thumbnails = (Map<String, Object>) snippet.get("thumbnails");

			Map<String, Object> def = (Map<String, Object>) thumbnails.get("default");

			request.setAttribute("itemsList", itemsList);

			request.setAttribute("titleList", titleList);

			request.setAttribute("videoIdList", videoIdList);
		}

		for (int i = 0; i < itemsList.size(); i++) {

			result = v3Videos.getData((String) videoIdList.get(i));

			Map<String, Object> map2 = (Map<String, Object>) parser.parse(result);

			List<Map<String, Object>> itemsList2 = (List<Map<String, Object>>) map2.get("items");

			Map<String, Object> items = itemsList2.get(0);

			Map<String, Object> contentDetails = (Map<String, Object>) items.get("contentDetails");

			String duration = (String) contentDetails.get("duration");

			int hour, minute, second;

			String str_hour = "00:";

			String str_minute = "00:";

			String str_second = "00";

			if (duration.contains("H") && duration.contains("M") && duration.contains("S")) {

				hour = Integer.parseInt(duration.substring(duration.indexOf("T") + 1, duration.indexOf("H")));

				minute = Integer.parseInt(duration.substring(duration.indexOf("H") + 1, duration.indexOf("M")));

				second = Integer.parseInt(duration.substring(duration.indexOf("M") + 1, duration.indexOf("S")));

				if (hour >= 10) {

					str_hour = hour + ":";
				} else if (hour < 10) {

					str_hour = "0" + hour + ":";
				}

				if (minute >= 10) {

					str_minute = minute + ":";
				} else if (minute < 10) {

					str_minute = "0" + minute + ":";
				}

				if (second >= 10) {

					str_second = second + "";
				} else if (second < 10) {

					str_second = "0" + second;
				}

				duration_HMSList.add(str_hour + str_minute + str_second);

				duration_SecList.add(hour * 3600 + minute * 60 + second);

				request.setAttribute("duration_HMSList", duration_HMSList);

				request.setAttribute("duration_SecList", duration_SecList);
			} else if (duration.contains("H") && duration.contains("M") && !duration.contains("S")) {

				hour = Integer.parseInt(duration.substring(duration.indexOf("T") + 1, duration.indexOf("H")));

				minute = Integer.parseInt(duration.substring(duration.indexOf("H") + 1, duration.indexOf("M")));

				if (hour >= 10) {

					str_hour = hour + ":";
				} else if (hour < 10) {

					str_hour = "0" + hour + ":";
				}

				if (minute >= 10) {

					str_minute = minute + ":";
				} else if (minute < 10) {

					str_minute = "0" + minute + ":";
				}

				duration_HMSList.add(str_hour + str_minute + str_second);

				duration_SecList.add(hour * 3600 + minute * 60);

				request.setAttribute("duration_HMSList", duration_HMSList);

				request.setAttribute("duration_SecList", duration_SecList);
			} else if (duration.contains("H") && !duration.contains("M") && duration.contains("S")) {

				hour = Integer.parseInt(duration.substring(duration.indexOf("T") + 1, duration.indexOf("H")));

				second = Integer.parseInt(duration.substring(duration.indexOf("H") + 1, duration.indexOf("S")));

				if (hour >= 10) {

					str_hour = hour + ":";
				} else if (hour < 10) {

					str_hour = "0" + hour + ":";
				}

				if (second >= 10) {

					str_second = second + "";
				} else if (second < 10) {

					str_second = "0" + second;
				}

				duration_HMSList.add(str_hour + str_minute + str_second);

				duration_SecList.add(hour * 3600 + second);

				request.setAttribute("duration_HMSList", duration_HMSList);

				request.setAttribute("duration_SecList", duration_SecList);
			} else if (duration.contains("H") && !duration.contains("M") && !duration.contains("S")) {

				hour = Integer.parseInt(duration.substring(duration.indexOf("T") + 1, duration.indexOf("H")));

				if (hour >= 10) {

					str_hour = hour + ":";
				} else if (hour < 10) {

					str_hour = "0" + hour + ":";
				}

				duration_HMSList.add(str_hour + str_minute + str_second);

				duration_SecList.add(hour * 3600);

				request.setAttribute("duration_HMSList", duration_HMSList);

				request.setAttribute("duration_SecList", duration_SecList);
			} else if (!duration.contains("H") && duration.contains("M") && duration.contains("S")) {

				minute = Integer.parseInt(duration.substring(duration.indexOf("T") + 1, duration.indexOf("M")));

				second = Integer.parseInt(duration.substring(duration.indexOf("M") + 1, duration.indexOf("S")));

				if (minute >= 10) {

					str_minute = minute + ":";
				} else if (minute < 10) {

					str_minute = "0" + minute + ":";
				}

				if (second >= 10) {

					str_second = second + "";
				} else if (second < 10) {

					str_second = "0" + second;
				}

				duration_HMSList.add(str_hour + str_minute + str_second);

				duration_SecList.add(minute * 60 + second);

				request.setAttribute("duration_HMSList", duration_HMSList);

				request.setAttribute("duration_SecList", duration_SecList);
			} else if (!duration.contains("H") && duration.contains("M") && !duration.contains("S")) {

				minute = Integer.parseInt(duration.substring(duration.indexOf("T") + 1, duration.indexOf("M")));

				if (minute >= 10) {

					str_minute = minute + ":";
				} else if (minute < 10) {

					str_minute = "0" + minute + ":";
				}

				duration_HMSList.add(str_hour + str_minute + str_second);

				duration_SecList.add(minute * 60);

				request.setAttribute("duration_HMSList", duration_HMSList);

				request.setAttribute("duration_SecList", duration_SecList);
			} else if (!duration.contains("H") && !duration.contains("M") && duration.contains("S")) {

				second = Integer.parseInt(duration.substring(duration.indexOf("T") + 1, duration.indexOf("S")));

				if (second >= 10) {

					str_second = second + "";
				} else if (second < 10) {

					str_second = "0" + second;
				}

				duration_HMSList.add(str_hour + str_minute + str_second);

				duration_SecList.add(second);

				request.setAttribute("duration_HMSList", duration_HMSList);

				request.setAttribute("duration_SecList", duration_SecList);
			}
		}

		return "/musicPlayer/Searchlist";
	}

	@RequestMapping("/InsertPlaylist.com")
	public String insertPlaylist(HttpServletRequest request, PlaylistDTO dto) throws Exception {
		
		int check = (int) sqlMap.selectOne("checkPlaylist", dto);

		if (check == 0) {

			sqlMap.insert("insertPlaylist", dto);
		}
		
		request.setAttribute("check", check);
		return "/musicPlayer/InsertPlaylist";
	}

	@RequestMapping("/SelectPlaylist.com")
	public String selectPlaylist(HttpServletRequest request, PlaylistDTO dto) throws Exception {

		int oc_num = Integer.parseInt(request.getParameter("oc_num"));
		
		List<Map<String, Object>> playList = sqlMap.selectList("selectPlaylist", oc_num);
		if(playList.size() > 0 ) {
			String[] videoIdArray = new String[playList.size()];

			String[] titleArray = new String[playList.size()];

			String[] durationArray = new String[playList.size()];

			String[] numArray = new String[playList.size()];

			// String[] nameArray = new String[playList.size()];

			int hour, minute, second;

			String str_hour = "00:";

			String str_minute = "00:";

			String str_second = "00";

			for (int i = 0; i < playList.size(); i++) {

				Map<String, Object> list = playList.get(i);

				String videoId = (String) list.get("pl_videoId");

				videoIdArray[i] = videoId;

				String title = (String) list.get("pl_title");

				titleArray[i] = title;

				int duration = Integer.parseInt(String.valueOf(list.get("pl_duration")));

				if (duration >= 3600) {

					hour = duration / 3600;

					minute = (duration % 3600) / 60;

					second = (duration % 3600) % 60;

					if (hour > 0) {

						if (hour >= 10) {

							str_hour = hour + ":";
						} else if (hour < 10) {

							str_hour = "0" + hour + ":";
						}
					}

					if (minute > 0) {

						if (minute >= 10 && minute < 60) {

							str_minute = minute + ":";
						} else if (minute < 10) {

							str_minute = "0" + minute + ":";
						}
					}

					if (second > 0) {

						if (second >= 10 && second < 60) {

							str_second = second + "";
						} else if (second < 10) {

							str_second = "0" + second;
						}
					}

					durationArray[i] = (str_hour + str_minute + str_second);
				} else if (duration >= 60) {

					minute = duration / 60;

					second = duration % 60;

					if (minute > 0) {

						if (minute >= 10 && minute < 60) {

							str_minute = minute + ":";
						} else if (minute < 10) {

							str_minute = "0" + minute + ":";
						}
					}

					if (second > 0) {

						if (second >= 10 && second < 60) {

							str_second = second + "";
						} else if (second < 10) {

							str_second = "0" + second;
						}
					}

					durationArray[i] = (str_hour + str_minute + str_second);
				} else if (duration >= 0) {

					second = duration;

					if (second > 0) {

						if (second >= 10 && second < 60) {

							str_second = second + "";
						} else if (second < 10) {

							str_second = "0" + second;
						}
					}

					durationArray[i] = (str_hour + str_minute + str_second);
				}

				int num = Integer.parseInt(String.valueOf(list.get("m_num")));

				String m_name = sqlMap.selectOne("chating.selectM_name", num);
				
				numArray[i] = m_name;

				// String name = sqlMap.selectOne("", num);

				// nameArray[i] = name;
			}

			request.setAttribute("playList", playList);

			request.setAttribute("videoIdArray", videoIdArray);

			request.setAttribute("titleArray", titleArray);

			request.setAttribute("durationArray", durationArray);

			request.setAttribute("numArray", numArray);

			// request.setAttribute("nameArray", nameArray);

		}
		
		System.out.println("사이즈 : " + playList.size());
		return "/musicPlayer/SelectPlaylist";
	}

	@RequestMapping("/DeletePlaylist.com")
	public String deletePlaylist(HttpServletRequest request, PlaylistDTO dto) throws Exception {

		String pl_videoId = request.getParameter("pl_videoId");

		int oc_num = Integer.parseInt(request.getParameter("oc_num"));

		dto.setPl_videoId(pl_videoId);

		dto.setOc_num(oc_num);

		int check = (int) sqlMap.selectOne("checkPlaylist", dto);

		if (check == 1) {

			sqlMap.delete("deletePlaylist", dto);
		}

		List<Map<String, Object>> playList = sqlMap.selectList("selectPlaylist", oc_num);

		if(playList.size() > 0 ) {
			String[] videoIdArray = new String[playList.size()];

			String[] titleArray = new String[playList.size()];

			String[] durationArray = new String[playList.size()];

			int[] numArray = new int[playList.size()];

			int hour, minute, second;

			String str_hour = "00:";

			String str_minute = "00:";

			String str_second = "00";

			for (int i = 0; i < playList.size(); i++) {

				Map<String, Object> list = playList.get(i);

				String videoId = (String) list.get("pl_videoId");

				videoIdArray[i] = videoId;

				String title = (String) list.get("pl_title");

				titleArray[i] = title;

				int duration = Integer.parseInt(String.valueOf(list.get("pl_duration")));

				if (duration >= 3600) {

					hour = duration / 3600;

					minute = (duration % 3600) / 60;

					second = (duration % 3600) % 60;

					if (hour > 0) {

						if (hour >= 10) {

							str_hour = hour + ":";
						} else if (hour < 10) {

							str_hour = "0" + hour + ":";
						}
					}

					if (minute > 0) {

						if (minute >= 10 && minute < 60) {

							str_minute = minute + ":";
						} else if (minute < 10) {

							str_minute = "0" + minute + ":";
						}
					}

					if (second > 0) {

						if (second >= 10 && second < 60) {

							str_second = second + "";
						} else if (second < 10) {

							str_second = "0" + second;
						}
					}

					durationArray[i] = (str_hour + str_minute + str_second);
				} else if (duration >= 60) {

					minute = duration / 60;

					second = duration % 60;

					if (minute > 0) {

						if (minute >= 10 && minute < 60) {

							str_minute = minute + ":";
						} else if (minute < 10) {

							str_minute = "0" + minute + ":";
						}
					}

					if (second > 0) {

						if (second >= 10 && second < 60) {

							str_second = second + "";
						} else if (second < 10) {

							str_second = "0" + second;
						}
					}

					durationArray[i] = (str_hour + str_minute + str_second);
				} else if (duration >= 0) {

					second = duration;

					if (second > 0) {

						if (second >= 10 && second < 60) {

							str_second = second + "";
						} else if (second < 10) {

							str_second = "0" + second;
						}
					}

					durationArray[i] = (str_hour + str_minute + str_second);
				}

				int num = Integer.parseInt(String.valueOf(list.get("m_num")));

				numArray[i] = num;
			}

			request.setAttribute("playList", playList);

			request.setAttribute("videoIdArray", videoIdArray);

			request.setAttribute("titleArray", titleArray);

			request.setAttribute("durationArray", durationArray);

			request.setAttribute("numArray", numArray);

		}
		
		
		return "/musicPlayer/DeletePlaylist";
	}

	@RequestMapping("/PlayNext.com")
	public String playNext(HttpServletRequest request, PlaylistDTO dto) throws Exception {

		int oc_num = Integer.parseInt(request.getParameter("oc_num"));

		int trackNum = Integer.parseInt(request.getParameter("trackNum"));

		List<Map<String, Object>> playList = sqlMap.selectList("selectPlaylist", oc_num);

		if(playList.size() > 0 ) {
			int trackNum_Next = trackNum + 1;

			if (trackNum_Next == playList.size() + 1) {

				trackNum_Next = 1;
			}

			String videoId;

			String title;

			int duration;

			int hour, minute, second;

			String duration_HMS;

			int num;

			String str_hour = "00:";

			String str_minute = "00:";

			String str_second = "00";

			Map<String, Object> list = playList.get(trackNum_Next - 1);

			videoId = (String) list.get("pl_videoId");

			title = (String) list.get("pl_title");

			duration = Integer.parseInt(String.valueOf(list.get("pl_duration")));

			if (duration >= 3600) {

				hour = duration / 3600;

				minute = (duration % 3600) / 60;

				second = (duration % 3600) % 60;

				if (hour > 0) {

					if (hour >= 10) {

						str_hour = hour + ":";
					} else if (hour < 10) {

						str_hour = "0" + hour + ":";
					}
				}

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 60) {

				minute = duration / 60;

				second = duration % 60;

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 0) {

				second = duration;

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			}

			num = Integer.parseInt(String.valueOf(list.get("m_num")));

			String m_name = sqlMap.selectOne("chating.selectM_name", num);
			
			request.setAttribute("m_name", m_name);
			
			request.setAttribute("playList", playList);

			request.setAttribute("trackNum_Next", trackNum_Next);

			request.setAttribute("videoId", videoId);

			request.setAttribute("title", title);

			request.setAttribute("duration_Sec", duration);

			request.setAttribute("num", num);

			System.out.println("현재 트랙: " + trackNum_Next + "번 // 전체 곡 수: " + playList.size() + "개");

		}
		
		return "/musicPlayer/PlayNext";
	}

	@RequestMapping("/PlayPrevious.com")
	public String playPrevious(HttpServletRequest request, PlaylistDTO dto) throws Exception {

		int oc_num = Integer.parseInt(request.getParameter("oc_num"));

		int trackNum = Integer.parseInt(request.getParameter("trackNum"));

		List<Map<String, Object>> playList = sqlMap.selectList("selectPlaylist", oc_num);

		if(playList.size() > 0 ) {
			int trackNum_Previous = trackNum - 1;

			if (trackNum_Previous == 0) {

				trackNum_Previous = playList.size();
			}

			String videoId;

			String title;

			int duration;

			int hour, minute, second;

			String duration_HMS;

			int num;

			String str_hour = "00:";

			String str_minute = "00:";

			String str_second = "00";

			Map<String, Object> list = playList.get(trackNum_Previous - 1);

			videoId = (String) list.get("pl_videoId");

			title = (String) list.get("pl_title");

			duration = Integer.parseInt(String.valueOf(list.get("pl_duration")));

			if (duration >= 3600) {

				hour = duration / 3600;

				minute = (duration % 3600) / 60;

				second = (duration % 3600) % 60;

				if (hour > 0) {

					if (hour >= 10) {

						str_hour = hour + ":";
					} else if (hour < 10) {

						str_hour = "0" + hour + ":";
					}
				}

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 60) {

				minute = duration / 60;

				second = duration % 60;

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 0) {

				second = duration;

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			}

			num = Integer.parseInt(String.valueOf(list.get("m_num")));

			String m_name = sqlMap.selectOne("chating.selectM_name", num);
			
			request.setAttribute("m_name", m_name);
			
			request.setAttribute("playList", playList);

			request.setAttribute("trackNum_Previous", trackNum_Previous);

			request.setAttribute("videoId", videoId);

			request.setAttribute("title", title);

			request.setAttribute("duration_Sec", duration);

			request.setAttribute("num", num);

			System.out.println("현재 트랙: " + trackNum_Previous + "번 // 전체 곡 수: " + playList.size() + "개");

		}
		
		return "/musicPlayer/PlayPrevious";
	}

	@RequestMapping("/PlayRandom.com")
	public String playRandom(HttpServletRequest request, PlaylistDTO dto) throws Exception {

		int oc_num = Integer.parseInt(request.getParameter("oc_num"));

		int trackNum = Integer.parseInt(request.getParameter("trackNum"));

		List<Map<String, Object>> playList = sqlMap.selectList("selectPlaylist", oc_num);

		if(playList.size() > 0 ) {
			int trackNum_Random = (int) (Math.random() * playList.size()) + 1;

			String videoId;

			String title;

			int duration;

			int hour, minute, second;

			String duration_HMS;

			int num;

			String str_hour = "00:";

			String str_minute = "00:";

			String str_second = "00";

			Map<String, Object> list = playList.get(trackNum_Random - 1);

			videoId = (String) list.get("pl_videoId");

			title = (String) list.get("pl_title");

			duration = Integer.parseInt(String.valueOf(list.get("pl_duration")));

			if (duration >= 3600) {

				hour = duration / 3600;

				minute = (duration % 3600) / 60;

				second = (duration % 3600) % 60;

				if (hour > 0) {

					if (hour >= 10) {

						str_hour = hour + ":";
					} else if (hour < 10) {

						str_hour = "0" + hour + ":";
					}
				}

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 60) {

				minute = duration / 60;

				second = duration % 60;

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 0) {

				second = duration;

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			}

			num = Integer.parseInt(String.valueOf(list.get("m_num")));

			String m_name = sqlMap.selectOne("chating.selectM_name", num);
			
			request.setAttribute("m_name", m_name);
			
			request.setAttribute("playList", playList);

			request.setAttribute("trackNum_Random", trackNum_Random);

			request.setAttribute("videoId", videoId);

			request.setAttribute("title", title);

			request.setAttribute("duration_Sec", duration);

			request.setAttribute("num", num);
			
			System.out.println("현재 트랙: " + trackNum_Random + "번 // 전체 곡 수: " + playList.size() + "개");

		}
		
		return "/musicPlayer/PlayRandom";
	}
	
	
	@RequestMapping("/PlayTrack.com")
	public String playTrack(HttpServletRequest request, PlaylistDTO dto) throws Exception {

		int oc_num = Integer.parseInt(request.getParameter("oc_num"));

		int trackNum = Integer.parseInt(request.getParameter("trackNum"));

		List<Map<String, Object>> playList = sqlMap.selectList("selectPlaylist", oc_num);

		if(playList.size() > 0 ) {
			String videoId;

			String title;

			int duration;

			int hour, minute, second;

			String duration_HMS;

			int num;

			String str_hour = "00:";

			String str_minute = "00:";

			String str_second = "00";

			Map<String, Object> list = playList.get(trackNum - 1);

			videoId = (String) list.get("pl_videoId");

			title = (String) list.get("pl_title");

			duration = Integer.parseInt(String.valueOf(list.get("pl_duration")));

			if (duration >= 3600) {

				hour = duration / 3600;

				minute = (duration % 3600) / 60;

				second = (duration % 3600) % 60;

				if (hour > 0) {

					if (hour >= 10) {

						str_hour = hour + ":";
					} else if (hour < 10) {

						str_hour = "0" + hour + ":";
					}
				}

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 60) {

				minute = duration / 60;

				second = duration % 60;

				if (minute > 0) {

					if (minute >= 10 && minute < 60) {

						str_minute = minute + ":";
					} else if (minute < 10) {

						str_minute = "0" + minute + ":";
					}
				}

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			} else if (duration >= 0) {

				second = duration;

				if (second > 0) {

					if (second >= 10 && second < 60) {

						str_second = second + "";
					} else if (second < 10) {

						str_second = "0" + second;
					}
				}

				duration_HMS = (str_hour + str_minute + str_second);

				request.setAttribute("duration_HMS", duration_HMS);
			}

			num = Integer.parseInt(String.valueOf(list.get("m_num")));

			String m_name = sqlMap.selectOne("chating.selectM_name", num);
			
			request.setAttribute("m_name", m_name);
			
			request.setAttribute("playList", playList);

			request.setAttribute("trackNum", trackNum);

			request.setAttribute("videoId", videoId);

			request.setAttribute("title", title);

			request.setAttribute("duration_Sec", duration);

			request.setAttribute("num", num);

			System.out.println("현재 트랙: " + trackNum + "번 // 전체 곡 수: " + playList.size() + "개");

		}
		
		return "/musicPlayer/PlayTrack";
	}

	@RequestMapping("/Preview.com")
	public String preview(HttpServletRequest request) throws Exception {

		String videoId = request.getParameter("videoId");

		String title = request.getParameter("title");

		String duration_HMS = request.getParameter("duration_HMS");

		String duration_Sec = request.getParameter("duration_Sec");

		request.setAttribute("videoId", videoId);

		request.setAttribute("title", title);

		request.setAttribute("duration_HMS", duration_HMS);

		request.setAttribute("duration_Sec", duration_Sec);

		return "/musicPlayer/Preview";
	}
}