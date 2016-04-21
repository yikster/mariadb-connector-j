/*
MariaDB Client for Java

Copyright (c) 2015 Monty Program Ab.

This library is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation; either version 2.1 of the License, or (at your option)
any later version.

This library is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
for more details.

You should have received a copy of the GNU Lesser General Public License along
with this library; if not, write to Monty Program Ab info@montyprogram.com.

This particular MariaDB Client for Java file is work
derived from a Drizzle-JDBC. Drizzle-JDBC file which is covered by subject to
the following copyright and notice provisions:

Copyright (c) 2009-2011, Marcus Eriksson

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of the driver nor the names of its contributors may not be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS  AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
OF SUCH DAMAGE.
*/

package org.mariadb.jdbc.internal.util.scheduler;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public interface DynamicSizedSchedulerInterface extends ScheduledExecutorService {
    /**
     * Update the pool thread count with this new value.
     *
     * @param newSize New pool size that is superior to 0
     */
    public void setPoolSize(int newSize);
    
    /**
     * Adjusts the pool size by a given delta.  This should atomically change the pool size in a 
     * thread safe way.
     * 
     * @param delta A positive or negative number to adjust the pool size by.
     */
    public void adjustPoolSize(int delta);
    
    /**
     * Used when you want to ensure an extra thread is available to execute task.  This will adjust 
     * the pool size so that a thread can be created (if necessary), and then execute the task.  
     * Once the task completes the pool size will be adjusted back down.
     * 
     * @param task Task to be executed
     * @return Future which represents task execution
     */
    public Future<?> addThreadAndExecute(Runnable task);

    /**
     * Used when you want to ensure an extra thread is available for scheduled task.  This will 
     * adjust the pool size so that a thread can be created (if necessary), and then execute the 
     * task.  Once the task completes the pool size will be adjusted back down.
     * 
     * @param task Task to be executed
     * @param delay delay before execution
     * @param unit unit of delay
     * @return Future which represents task execution
     */
    public Future<?> addThreadAndSchedule(Runnable task, long delay, TimeUnit unit);
}