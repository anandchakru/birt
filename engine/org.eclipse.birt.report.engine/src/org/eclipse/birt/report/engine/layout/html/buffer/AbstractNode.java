/***********************************************************************
 * Copyright (c) 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.report.engine.layout.html.buffer;

import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.emitter.ContentEmitterUtil;
import org.eclipse.birt.report.engine.emitter.IContentEmitter;

public abstract class AbstractNode implements INode
{

	protected IContent content;
	protected IContentEmitter emitter;
	boolean isFirst = true;
	protected boolean finished = true;
	protected IContainerNode parent;
	protected boolean isStarted = false;
	protected PageHintGenerator generator;

	AbstractNode( IContent content, IContentEmitter emitter,
			PageHintGenerator generator )
	{
		this.content = content;
		this.emitter = emitter;
		this.generator = generator;
	}

	public void setFirst( boolean isFirst )
	{
		this.isFirst = isFirst;
	}

	public void setStarted( boolean isStarted )
	{
		this.isStarted = isStarted;
	}
	
	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}

	public boolean isStarted( )
	{
		return isStarted;
	}

	public void end( )
	{
		ContentEmitterUtil.endContent( content, emitter );
		generator.end( content, finished );
	}

	
	public void setParent( IContainerNode parent )
	{
		this.parent = parent;
	}

	public IContainerNode getParent( )
	{
		return parent;
	}

	public void start( )
	{
		if(isStarted)
		{
			return;
		}
		if ( parent != null && !parent.isStarted( ) )
		{
			parent.start( );
		}
		ContentEmitterUtil.startContent( content, emitter );
		generator.start( content, isFirst );
		isStarted = true;

	}

}
