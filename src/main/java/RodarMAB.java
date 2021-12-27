import java.security.InvalidParameterException;

public class RodarMAB {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Selecione o método de seleção de transcodificadores:");
			System.out.println("    1: Epsilon greedy ");
			System.out.println("    2: Epsilon first ");
			System.out.println("    3: Decreasing epsilon ");			
			System.out.println("    4: UCB1 ");
			System.out.println("    5: Epsilon greedy - untrustfull agents ");
			System.out.println("    6: Epsilon first - untrustfull agents  ");			
			System.out.println("    7: Decreasing epsilon - untrustfull agents ");
			System.out.println("    8: UCB1 - untrustfull agents ");			
			System.out.println("    9: Epsilon greedy - untrustfull agents - trust ");
			System.out.println("   10: Epsilon first - untrustfull agents - trust  ");			
			System.out.println("   11: Decreasing epsilon - untrustfull agents - trust ");
			System.out.println("   12: UCB1 - untrustfull agents - trust ");	
			System.out.println("   13: Epsilon greedy - untrustfull agents - trust ");
			System.out.println("   14: Epsilon first - untrustfull agents - trust  ");			
			System.out.println("   15: Decreasing epsilon - untrustfull agents - trust ");
			System.out.println("   16: UCB1 - untrustfull agents - trust ");				
		}
		
		try {
			jade.Boot.main(selecionaConfiguracao(args[0]));		
		} catch (InvalidParameterException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public static String[] selecionaConfiguracao(String selecao) {
		if ("1".contentEquals(selecao)) {
			System.out.println("Selecionando Epsilon greedy...");
			return configuracaoEpsilonGreedy();
		} else if ("2".contentEquals(selecao)) {
			System.out.println("Selecionando Epsilon first...");
			return configuracaoEpsilonFirst();
		} else if ("3".contentEquals(selecao)) {
			System.out.println("Selecionando Decreasing epsilon...");
			return configuracaoDecreasingEpsilon();
		} else if ("4".contentEquals(selecao)) {
			System.out.println("Selecionando UCB1...");
			return configuracaoUCB1();
		} else if ("5".contentEquals(selecao)) {
			System.out.println("Selecionando Epsilon greedy - untrustfull agents...");
			return configuracaoEpsilonGreedyUntrust();
		} else if ("6".contentEquals(selecao)) {
				System.out.println("Selecionando Epsilon first - untrustfull agents...");
				return configuracaoEpsilonFirstUntrust();			
		} else if ("7".contentEquals(selecao)) {
			System.out.println("Selecionando Decreasing epsilon - untrustfull agents...");
			return configuracaoDecreasingEpsilonUntrust();			
		} else if ("8".contentEquals(selecao)) {
			System.out.println("Selecionando UCB1 - untrustfull agents...");
			return configuracaoUCB1Untrust();
		} else if ("9".contentEquals(selecao)) {
			System.out.println("Selecionando Epsilon greedy - untrustfull agents - Trust...");
			return configuracaoEpsilonGreedyUntrustWithTrust();
		} else if ("10".contentEquals(selecao)) {
				System.out.println("Selecionando Epsilon first - untrustfull agents - Trust...");
				return configuracaoEpsilonFirstUntrustWithTrust();			
		} else if ("11".contentEquals(selecao)) {
			System.out.println("Selecionando Decreasing epsilon - untrustfull agents - Trust...");
			return configuracaoDecreasingEpsilonUntrustTrust();			
		} else if ("12".contentEquals(selecao)) {
			System.out.println("Selecionando UCB1 - untrustfull agents - Trust...");
			return configuracaoUCB1UntrustWithTrust();		
		} else if ("13".contentEquals(selecao)) {
			System.out.println("Selecionando Epsilon greedy - Trust...");
			return configuracaoEpsilonGreedyWithTrust();
		} else if ("14".contentEquals(selecao)) {
			System.out.println("Selecionando Epsilon first - Trust...");
			return configuracaoEpsilonFirstWithTrust();			
		} else if ("15".contentEquals(selecao)) {
			System.out.println("Selecionando Decreasing epsilon - Trust...");
			return configuracaoDecreasingEpsilonWithTrust();			
		} else if ("16".contentEquals(selecao)) {
			System.out.println("Selecionando UCB1 - Trust...");
			return configuracaoUCB1WithTrust();			
		} else {
			throw new InvalidParameterException("Configuração " + selecao + " não reconhecida!");
		}
	}
	
	private static String[] configuracaoUCB1WithTrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"ucbufb1:br.unb.cic.comnet.distran.agents.broker.mab.MabUCBBrokerFIRE"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)	
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"
			};
	}

	private static String[] configuracaoDecreasingEpsilonWithTrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"deufb1:br.unb.cic.comnet.distran.agents.broker.mab.DecreasingEpsilonBrokerFIRE"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)	
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoEpsilonFirstWithTrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"efufb1:br.unb.cic.comnet.distran.agents.broker.mab.EpsilonFirstBrokerFIRE"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)	
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoEpsilonGreedyWithTrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"egufb1:br.unb.cic.comnet.distran.agents.broker.mab.EpsilonGreedyBrokerFIRE"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)	
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoUCB1UntrustWithTrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"ucbufb1:br.unb.cic.comnet.distran.agents.broker.mab.MabUCBBrokerFIRE"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"
			};
	}

	private static String[] configuracaoDecreasingEpsilonUntrustTrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"deufb1:br.unb.cic.comnet.distran.agents.broker.mab.DecreasingEpsilonBrokerFIRE"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"		
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoEpsilonFirstUntrustWithTrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"efufb1:br.unb.cic.comnet.distran.agents.broker.mab.EpsilonFirstBrokerFIRE"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"		
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};	
	}

	private static String[] configuracaoEpsilonGreedyUntrustWithTrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"egufb1:br.unb.cic.comnet.distran.agents.broker.mab.EpsilonGreedyBrokerFIRE"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"	
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoUCB1Untrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"ucbub1:br.unb.cic.comnet.distran.agents.broker.mab.MabUCBBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"
			};
	}

	private static String[] configuracaoDecreasingEpsilonUntrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"deub1:br.unb.cic.comnet.distran.agents.broker.mab.DecreasingEpsilonBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"		
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoEpsilonFirstUntrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"efub1:br.unb.cic.comnet.distran.agents.broker.mab.EpsilonFirstBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"		
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};	
	}

	private static String[] configuracaoEpsilonGreedyUntrust() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"egub1:br.unb.cic.comnet.distran.agents.broker.mab.EpsilonGreedyBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"	
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoUCB1() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"ucbb1:br.unb.cic.comnet.distran.agents.broker.mab.MabUCBBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoDecreasingEpsilon() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"deb1:br.unb.cic.comnet.distran.agents.broker.mab.DecreasingEpsilonBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoEpsilonFirst() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"efb1:br.unb.cic.comnet.distran.agents.broker.mab.EpsilonFirstBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	private static String[] configuracaoEpsilonGreedy() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"egb1:br.unb.cic.comnet.distran.agents.broker.mab.EpsilonGreedyBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};
	}

	public static String[] configuracaoRandom() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"br1:br.unb.cic.comnet.distran.agents.broker.randomChoice.RandomChoiceBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(18)	
						+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
						+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"	
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};		
	}	
	
	public static String[] configuracaoRandomSemWhiteWashing() {
		return new String[] {
				"-gui", 
				" -agents " + 
						"br2:br.unb.cic.comnet.distran.agents.broker.randomChoice.RandomChoiceBroker"  
						+ montaRandomTranscoders("ABBCC")  
						+ montaSequentialPlayers(20)		
						+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"						
			};		
	}
	
	public static String[] configuracaoUCB() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"bu1:br.unb.cic.comnet.distran.agents.broker.mab.MabUCBBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(18)						
					+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
					+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"				
					+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"					
		};
	}	
	
	public static String[] configuracaoUCBSemWhiteWashing() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"bu2:br.unb.cic.comnet.distran.agents.broker.mab.MabUCBBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(20)				
					+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"					
		};
	}		
	
	public static String[] configuracaoReNoS() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"br1:br.unb.cic.comnet.distran.agents.broker.FIRE.FIRETRMBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(18)						
					+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
					+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"		
					+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"					
		};
	}
	
	public static String[] configuracaoReNoSSemWhiteWashing() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"br2:br.unb.cic.comnet.distran.agents.broker.FIRE.FIRETRMBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(20)			
					+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"					
		};
	}	
	
	public static String[] configuracaoReNoSAlterado() {
		return new String[] {
				"-gui", 
				" -agents " + 
					"bra1:br.unb.cic.comnet.distran.agents.broker.FIRE.ModifiedFIRETRMBroker" 
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(18)						
					+ ";uv1:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"  					
					+ ";uv2:br.unb.cic.comnet.distran.agents.viewer.untrustable.UntrustableSequentialPlayViewer(A, tc5)"	
					+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"					
		};
	}	
	
	public static String[] configuracaoReNoSAlteradoSemWhiteWashing() {
		return new String[] {
			"-gui", 
			" -agents " + 
					"bra2:br.unb.cic.comnet.distran.agents.broker.FIRE.ModifiedFIRETRMBroker"  
					+ montaRandomTranscoders("ABBCC")  
					+ montaSequentialPlayers(20)			
					+ ";stp:br.unb.cic.comnet.distran.agents.StopperAgent"					
		};
	}	
	
	private static String montaRandomTranscoders(String profiles) {
		return montaProfiles(profiles, "t", "br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder");
	}		
	
	private static String montaSequentialPlayers(int count) {
		return montaStringAgente("v", "br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer", count);
	}	
	
	private static String montaProfiles(String profiles, String prefixo, String classe) {
		StringBuilder resultado = new StringBuilder();
		
		int count = 1;
		for(char a: profiles.toCharArray()) {
			StringBuilder builder = new StringBuilder();
			builder.append(";");
			builder.append(prefixo);
			builder.append(Character.toLowerCase(a));
			builder.append(count++);
			builder.append(":");
			builder.append(classe);
			builder.append("(");
			builder.append(a);
			builder.append(")");
			
			resultado.append(builder.toString());
		}
		
		return resultado.toString();
	}

	private static String montaStringAgente(String prefixo, String classe, int count) {
		StringBuilder resultado = new StringBuilder();
		
		for(int i = 1; i <= count; i++) {
			StringBuilder builder = new StringBuilder();
			
			builder.append(";");			
			builder.append(prefixo);
			builder.append(i);
			builder.append(":");
			builder.append(classe);
			
			resultado.append(builder.toString());
		}
		
		return resultado.toString();		
	}	
	
	/*
	public static void main(String[] args) {
		String[] rodarDistran = new String[] {
				"-gui", 
				" -agents " + 
					"b1:br.unb.cic.comnet.distran.agents.broker.SequentialBroker(R);" + 
					"ta1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(A);" +
					"tb1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(B);" +					
					"tb2:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(B);" +
					"tc1:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(C);" +					
					"tc2:br.unb.cic.comnet.distran.agents.transcoder.RandomTimeTranscoder(C);" +
					"v1:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v2:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v3:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v4:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v5:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v6:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v7:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v8:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v9:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v10:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v11:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" +
					"v12:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v13:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v14:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v15:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v16:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v17:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v18:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v19:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v20:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v21:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" + 
					"v22:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" +
					"v23:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer;" +					
					"v24:br.unb.cic.comnet.distran.agents.viewer.SequentialPlayViewer"};
		
		jade.Boot.main(rodarDistran);
	}	
*/	
	

}
